    package com.example.submissionawaldicoding.ui.detail

    import android.content.Intent
    import android.net.Uri
    import android.os.Build
    import android.os.Bundle
    import android.text.Html
    import android.util.Log
    import android.view.View
    import android.widget.Toast
    import androidx.activity.viewModels
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.Observer
    import androidx.room.RoomDatabase
    import com.bumptech.glide.Glide
    import com.example.submissionawaldicoding.R
    import com.example.submissionawaldicoding.data.local.entity.EventEntity
    import com.example.submissionawaldicoding.data.local.room.EventDatabase
    import com.example.submissionawaldicoding.data.remote.ListEventsItem
    import com.example.submissionawaldicoding.databinding.ActivityDetailBinding
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch

    class DetailActivity : AppCompatActivity() {

        private lateinit var binding: ActivityDetailBinding
        private val viewModel: DetailEventViewModel by viewModels()
        private lateinit var database: EventDatabase
        private var isFavorite = false

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)

            database = EventDatabase.getDatabase(this)

            val eventId = intent.getStringExtra("EVENT_ID")
            Log.d("DetailActivity", "Received EVENT_ID: $eventId")
            if (eventId != null) {
                showLoading(true)
                viewModel.fetchEventDetail(eventId)
                setupObservers()

                CoroutineScope(Dispatchers.IO).launch {
                    isFavorite = database.eventDao().getAllFavorites().any { it.id == eventId.toInt() }
                    runOnUiThread { updateFavoriteIcon() }
                }
            } else {
                Toast.makeText(this, "Event ID is missing", Toast.LENGTH_SHORT).show()
            }

            binding.btnRegister.setOnClickListener {
                val eventDetail = viewModel.eventDetail.value?.event
                if (eventDetail != null && !eventDetail.link.isNullOrEmpty()) {
                    openEventLink(eventDetail.link)
                } else {
                    Toast.makeText(this, "Event link is not available", Toast.LENGTH_SHORT).show()
                }
            }

            binding.ivFavorite.setOnClickListener {
                val eventDetail = viewModel.eventDetail.value?.event
                if (eventDetail != null) {
                    val eventEntity = EventEntity(
                        id = eventDetail.id,
                        name = eventDetail.name,
                        mediaCover = eventDetail.mediaCover,
                        link = eventDetail.link,
                        description = eventDetail.description,
                        ownerName = eventDetail.ownerName,
                        beginTime = eventDetail.beginTime,
                        endTime = eventDetail.endTime,
                        quota = eventDetail.quota,
                        registrants = eventDetail.registrants,
                        cityName = eventDetail.cityName,
                        category = eventDetail.category,
                        imageLogo = eventDetail.imageLogo,
                        summary = eventDetail.summary
                    )
                    addOrRemoveFavorite(eventEntity)
                }
            }
        }

        private fun setupObservers() {
            viewModel.eventDetail.observe(this, Observer { detailEventResponse ->
                showLoading(false)
                if (detailEventResponse != null) {
                    displayEventDetails(detailEventResponse.event)
                } else {
                    Toast.makeText(this, "Failed to load event details", Toast.LENGTH_SHORT).show()
                }
            })
        }

        private fun displayEventDetails(eventDetail: ListEventsItem) {
            Glide.with(this).load(eventDetail.mediaCover).into(binding.ivBanner)
            binding.tvName.text = eventDetail.name
            binding.tvOwner.text = eventDetail.ownerName
            binding.tvBeginTime.text = eventDetail.beginTime
            if (eventDetail.quota != null && eventDetail.registrants != null) {
                val slot = eventDetail.quota - eventDetail.registrants
                binding.tvSlot.text = "Sisa Kuota : $slot"
            } else {
                binding.tvSlot.text = "Sisa Kuota : N/A"
            }
            binding.tvQuota.text = "Kuota: ${eventDetail.quota}"
            binding.tvDescription.text = getHtml(eventDetail.description ?: "")

        }

        private fun showLoading(isLoading: Boolean) {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        private fun getHtml(htmlBody: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                Html.fromHtml(htmlBody, Html.FROM_HTML_MODE_LEGACY).toString()
            else
                Html.fromHtml(htmlBody).toString()
        }

        private fun openEventLink(url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        private fun addOrRemoveFavorite(event: EventEntity) {
            CoroutineScope(Dispatchers.IO).launch {
                val favoriteDao = database.eventDao()
                // Check if already favorite
                val favorite = favoriteDao.getAllFavorites()
                if (isFavorite) {
                    favoriteDao.deleteFavorite(event.id)
                    runOnUiThread {
                        isFavorite = false
                        updateFavoriteIcon()
                        Toast.makeText(
                            this@DetailActivity,
                            "Removed from favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    favoriteDao.insertFavorite(event)
                    runOnUiThread {
                        isFavorite = true
                        updateFavoriteIcon()
                        Toast.makeText(
                            this@DetailActivity,
                            "Added to favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        private fun updateFavoriteIcon() {
            binding.ivFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }
    }