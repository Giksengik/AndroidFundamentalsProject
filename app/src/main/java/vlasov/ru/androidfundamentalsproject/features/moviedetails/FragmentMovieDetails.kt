package vlasov.ru.androidfundamentalsproject.features.moviedetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.databinding.FragmentMovieDetailsBinding
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.models.Movie
import java.util.*


class FragmentMovieDetails : Fragment() {

    private val viewModel : MovieDetailsViewModel by viewModels{
        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var castAdapter: MovieCastAdapter
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var isRationaleShown = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){

        }
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_movie_details, container, false)
        val root = binding.root

        if(arguments != null){
                viewModel.setMovieData(requireArguments().getSerializable(MOVIE_ARG) as Movie?)
        }
        viewModel.movieData.observe(viewLifecycleOwner){
            setUIData(it)
        }
        root.findViewById<RecyclerView>(R.id.movieCastRecyclerView).apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            castAdapter = MovieCastAdapter()
            adapter = castAdapter
        }
        setUpListeners()

        return root
    }

    private fun onScheduleWatchingButtonClick() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED -> onReadCalendarPermissionGranted()
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR) ->
                    showCalendarPermissionExplanationDialog()
                isRationaleShown -> showCalendarReadPermissionDenied()
                else -> requestCalendarReadPermission()
            }
        }
    }

    private fun onReadCalendarPermissionGranted(){
        showDateTimePickerForWatching()
    }


    private fun showDateTimePickerForWatching() {
        activity?.let{
            val currentDate = Calendar.getInstance()
            val combinedCal: Calendar = GregorianCalendar(currentDate.timeZone)
            DatePickerDialog(it, R.style.ThemeOverlay_MaterialComponents_TimePicker, { view, year, monthOfYear, dayOfMonth ->
                TimePickerDialog(
                        context,
                        R.style.ThemeOverlay_MaterialComponents_TimePicker,
                        { view, hourOfDay, minute ->
                            combinedCal.set(year, monthOfYear, dayOfMonth)
                            combinedCal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            combinedCal.set(Calendar.MINUTE, minute)
                            val intent = Intent(ACTION_INSERT)
                            intent.data = CalendarContract.Events.CONTENT_URI
                            intent.putExtra(CalendarContract.Events.TITLE, getString(R.string.schedule_movie_watching_title))
                            intent.putExtra(EXTRA_EVENT_BEGIN_TIME, combinedCal.timeInMillis)
                            intent.flags = FLAG_ACTIVITY_NEW_TASK
                            if(intent.resolveActivity(it.packageManager) != null) {
                                startActivity(intent)
                            }
                            else {
                                Toast.makeText(it, R.string.error_scheduling_calendar_event, Toast.LENGTH_SHORT).show()
                            }
                        },
                        currentDate[Calendar.HOUR_OF_DAY],
                        currentDate[Calendar.MINUTE],
                        false
                ).show()
            },
                    currentDate[Calendar.YEAR],
                    currentDate[Calendar.MONTH],
                    currentDate[Calendar.DATE]).show()
        }
    }

    private fun showCalendarPermissionExplanationDialog(){
        activity?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                    .setMessage(R.string.permission_calendar_read_explanation_message)
                    .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                        isRationaleShown = true
                        requestCalendarReadPermission()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.dialog_negative_button) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
        }

    }

    private fun requestCalendarReadPermission(){
        context.let {
            requestPermissionLauncher.launch(Manifest.permission.READ_CALENDAR)
        }
    }

    private fun showCalendarReadPermissionDenied(){
            activity?.let{
                androidx.appcompat.app.AlertDialog.Builder(it)
                        .setMessage(R.string.permission_calendar_read_denied)
                        .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                            startActivity(
                                    Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.parse("package:" + it.packageName)
                                    )
                            )
                            dialog.dismiss()
                        }
                        .setNegativeButton(R.string.dialog_negative_button) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
            }

    }
    @SuppressLint("SetTextI18n")
    fun setUIData(movie: Movie) {
        binding.movie = movie
        binding.root.findViewById<ImageView>(R.id.moviePicture).load(movie.detailImageUrl)
        binding.root.findViewById<TextView>(R.id.movieNumOfReviews).text = movie.reviewCount.toString() + " " + getString(R.string.reviews)
        castAdapter.submitList(movie.actors)
        val starsImages = listOf(
                binding.movieDetailsStar1,
                binding.movieDetailsStar2,
                binding.movieDetailsStar3,
                binding.movieDetailsStar4,
                binding.movieDetailsStar5
        )
        starsImages.forEachIndexed { index, imageView ->
            val colorId = if (movie.rating / 2 > index) R.color.pink_light else R.color.gray_dark
            ImageViewCompat.setImageTintList(
                    imageView, ColorStateList.valueOf(
                    ContextCompat.getColor(imageView.context, colorId)
            )
            )
        }

    }

    private fun setUpListeners(){

        binding.root.findViewById<TextView>(R.id.backButtonMovie).setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.root.findViewById<Button>(R.id.scheduleWatchingButton).setOnClickListener{
            onScheduleWatchingButtonClick()
        }
    }

    override fun onDetach() {
        super.onDetach()
        requestPermissionLauncher.unregister()
    }



    companion object{
        val MOVIE_ARG = "vlasov.ru.androidfundamentalsproject.features.moviedetails.FragmentMovieDetails.movie"
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.features.moviedetails.FragmentMovieDetails"
        fun newInstance(movie: Movie) : FragmentMovieDetails {
            val args = Bundle()
            args.putSerializable(MOVIE_ARG, movie)
            val fragment = FragmentMovieDetails()
            fragment.arguments = args
            return fragment
        }

    }
}