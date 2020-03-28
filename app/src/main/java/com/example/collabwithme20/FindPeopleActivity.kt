package com.example.collabwithme20

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collabwithme20.Adapters.SearchUsersAdapter
import com.example.collabwithme20.Models.UserModel
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreArray
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_find_people.*
import kotlinx.android.synthetic.main.activity_profile.backBtn


class FindPeopleActivity : AppCompatActivity(), SearchUsersAdapter.OnUserClickListener {
    companion object {
        private const val TAG = "FindPeopleActivity"

    }
    //RecyclerView
    private lateinit var recyclerView: RecyclerView

    //Firebase
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: String()

    //Notification
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private var channelID = "com.example.collabwithme20"
    private var description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_people)

        backBtn.setOnClickListener {
            val intentGoToPreviousActivity = Intent(this, HomeScreenActivity::class.java)
            startActivity(
                intentGoToPreviousActivity,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }

        //Variable to assign style
        var allCitiesStyle = 0
        var londonStyle = 0
        var bristolStyle = 0
        var birminghamStyle = 0
        var manchesterStyle = 0
        var swanseaStyle = 0
        var dublinStyle = 0
        var cardiffStyle = 0
        var edinburghStyle = 0

        var allSkillsStyle = 0
        var musicProducerStyle = 0
        var singerStyle = 0
        var rapperStyle = 0
        var videoProductionStyle = 0
        var graphicDesignStyle = 0
        var clothingDesignStyle = 0
        var instrumentalistStyle = 0
        var soundEngineerStyle = 0



        allCitiesBtn.setBackgroundResource(R.drawable.style27)
        allSkillsBtn.setBackgroundResource(R.drawable.style27)

        createRecyclerView("All", "All")

        //Create recyclerview for each city category

        allCitiesBtn.setOnClickListener {
            if (allCitiesStyle == 1) {
                //allCitiesBtn.setBackgroundResource(R.drawable.style1)

                createRecyclerView("All", "All")
                allCitiesStyle = 0
            } else {
                allCitiesBtn.setBackgroundResource(R.drawable.style27)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("All", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("All", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("All", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("All", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("All", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("All", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("All", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("All", "sound_engineer")
                }


                allCitiesStyle = 1
                londonStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                manchesterStyle = 0
                swanseaStyle = 0
                dublinStyle = 0
                cardiffStyle = 0
                edinburghStyle = 0
            }
        }

        searchLondonBtn.setOnClickListener {
            if (londonStyle == 1) {
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                londonStyle = 0
            } else {
                searchLondonBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("London", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("London", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("London", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("London", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("London", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("London", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("London", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("London", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("London", "sound_engineer")
                }

                londonStyle = 1
                allCitiesStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                manchesterStyle = 0
                swanseaStyle = 0
                dublinStyle = 0
                cardiffStyle = 0
                edinburghStyle = 0
            }
        }

        searchBristolBtn.setOnClickListener {
            if (bristolStyle == 1) {
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                bristolStyle = 0
            } else {
                searchBristolBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Bristol", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Bristol", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Bristol", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Bristol", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Bristol", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Bristol", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Bristol", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Bristol", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Bristol", "sound_engineer")
                }

                bristolStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                birminghamStyle = 0
                manchesterStyle = 0
                swanseaStyle = 0
                dublinStyle = 0
                cardiffStyle = 0
                edinburghStyle = 0
            }
        }

        searchBirminghamBtn.setOnClickListener {
            if (birminghamStyle == 1) {
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                birminghamStyle = 0
            } else {
                searchBirminghamBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Birmingham", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Birmingham", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Birmingham", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Birmingham", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Birmingham", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Birmingham", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Birmingham", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Birmingham", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Birmingham", "sound_engineer")
                }

                birminghamStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                bristolStyle = 0
                manchesterStyle = 0
                swanseaStyle = 0
                dublinStyle = 0
                cardiffStyle = 0
                edinburghStyle = 0
            }
        }

        searchCardiffBtn.setOnClickListener {
            if (cardiffStyle == 1) {
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                cardiffStyle = 0
            } else {

                searchCardiffBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Cardiff", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Cardiff", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Cardiff", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Cardiff", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Cardiff", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Cardiff", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Cardiff", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Cardiff", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Cardiff", "sound_engineer")
                }

                cardiffStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                manchesterStyle = 0
                swanseaStyle = 0
                dublinStyle = 0
                edinburghStyle = 0
            }
        }

        searchDublinBtn.setOnClickListener {
            if (dublinStyle == 1) {
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                dublinStyle = 0
            } else {

                searchDublinBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Dublin", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Dublin", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Dublin", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Dublin", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Dublin", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Dublin", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Dublin", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Dublin", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Dublin", "sound_engineer")
                }

                dublinStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                cardiffStyle = 0
                manchesterStyle = 0
                swanseaStyle = 0
                edinburghStyle = 0
            }
        }

        searchEdinburghBtn.setOnClickListener {
            if (edinburghStyle == 1) {
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                edinburghStyle = 0
            } else {

                searchEdinburghBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Edinburgh", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Edinburgh", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Swansea", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Edinburgh", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Edinburgh", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Edinburgh", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Edinburgh", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Edinburgh", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Edinburgh", "sound_engineer")
                }

                edinburghStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                cardiffStyle = 0
                manchesterStyle = 0
                dublinStyle = 0
                swanseaStyle = 0

            }
        }

        searchManchesterBtn.setOnClickListener {
            if (manchesterStyle == 1) {
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                manchesterStyle = 0
            } else {

                searchManchesterBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)

                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Manchester", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Manchester", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Swansea", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Manchester", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Manchester", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Manchester", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Manchester", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Manchester", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Manchester", "sound_engineer")
                }

                manchesterStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                cardiffStyle = 0
                edinburghStyle = 0
                dublinStyle = 0
                swanseaStyle = 0

            }
        }
        searchSwanseaBtn.setOnClickListener {
            if (swanseaStyle == 1) {
                searchSwanseaBtn.setBackgroundResource(R.drawable.style1)
                allCitiesBtn.setBackgroundResource(R.drawable.style27)

                createRecyclerView("All", "All")
                swanseaStyle = 0
            } else {

                searchSwanseaBtn.setBackgroundResource(R.drawable.style27)
                allCitiesBtn.setBackgroundResource(R.drawable.style1)
                searchLondonBtn.setBackgroundResource(R.drawable.style1)
                searchBristolBtn.setBackgroundResource(R.drawable.style1)
                searchBirminghamBtn.setBackgroundResource(R.drawable.style1)
                searchCardiffBtn.setBackgroundResource(R.drawable.style1)
                searchEdinburghBtn.setBackgroundResource(R.drawable.style1)
                searchDublinBtn.setBackgroundResource(R.drawable.style1)
                searchManchesterBtn.setBackgroundResource(R.drawable.style1)


                if (allSkillsStyle == 1 || allSkillsStyle == 0) {
                    createRecyclerView("Swansea", "All")
                }
                if (musicProducerStyle == 1) {
                    createRecyclerView("Swansea", "music_production")
                }
                if (rapperStyle == 1) {
                    createRecyclerView("Swansea", "rapping")
                }
                if (singerStyle == 1) {
                    createRecyclerView("Swansea", "singing")
                }
                if (videoProductionStyle == 1) {
                    createRecyclerView("Swansea", "video_production")
                }
                if (graphicDesignStyle == 1) {
                    createRecyclerView("Swansea", "graphic_designer")
                }
                if (clothingDesignStyle == 1) {
                    createRecyclerView("Swansea", "clothing_design")
                }
                if (instrumentalistStyle == 1) {
                    createRecyclerView("Swansea", "instrumentalist")
                }
                if (soundEngineerStyle == 1) {
                    createRecyclerView("Swansea", "sound_engineer")
                }

                swanseaStyle = 1
                allCitiesStyle = 0
                londonStyle = 0
                bristolStyle = 0
                birminghamStyle = 0
                cardiffStyle = 0
                edinburghStyle = 0
                dublinStyle = 0
                manchesterStyle = 0

            }
        }

        allSkillsBtn.setOnClickListener {

            if (allSkillsStyle == 1) {

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                musicProducerStyle = 0
            } else {

                allSkillsBtn.setBackgroundResource(R.drawable.style27)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                allSkillsStyle = 1
                musicProducerStyle = 0
                singerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0
            }
        }

        searchMusicProducerBtn.setOnClickListener {

            if (musicProducerStyle == 1) {
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                musicProducerStyle = 0
            } else {

                searchMusicProducerBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "music_production")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "music_production")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "music_production")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "music_production")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "music_production")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "music_production")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "music_production")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "music_production")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "music_production")
                }

                musicProducerStyle = 1
                allSkillsStyle = 0
                singerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0
            }
        }

        searchSingerBtn.setOnClickListener {

            if (singerStyle == 1) {
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                singerStyle = 0
            } else {

                searchSingerBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "singing")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "singing")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "singing")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "singing")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "singing")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "singing")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "singing")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "singing")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "singing")
                }

                singerStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0
            }
        }

        searchRapperBtn.setOnClickListener {

            if (rapperStyle == 1) {
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                rapperStyle = 0
            } else {

                searchRapperBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "rapping")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "rapping")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "rapping")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "rapping")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "rapping")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "rapping")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "rapping")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "rapping")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "rapping")
                }

                rapperStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                singerStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0
            }
        }

        searchVideoProductionBtn.setOnClickListener {

            if (videoProductionStyle == 1) {
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                videoProductionStyle = 0
            } else {

                searchVideoProductionBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "video_production")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "video_production")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "video_production")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "video_production")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "video_production")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "video_production")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "video_production")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "video_production")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "video_production")
                }

                videoProductionStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                singerStyle = 0
                rapperStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0
            }
        }

        searchGraphicDesignerBtn.setOnClickListener {

            if (graphicDesignStyle == 1) {
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                graphicDesignStyle = 0
            } else {

                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "graphic_designer")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "graphic_designer")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "graphic_designer")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "graphic_designer")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "graphic_designer")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "graphic_designer")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "graphic_designer")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "graphic_designer")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "graphic_designer")
                }

                graphicDesignStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                singerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0
            }
        }

        searchClothingDesignBtn.setOnClickListener {

            if (clothingDesignStyle == 1) {
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                clothingDesignStyle = 0
            } else {

                searchClothingDesignBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "clothing_design")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "clothing_design")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "clothing_design")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "clothing_design")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "clothing_design")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "clothing_design")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "clothing_design")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "clothing_design")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "clothing_design")
                }

                clothingDesignStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                singerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                instrumentalistStyle = 0
                soundEngineerStyle = 0

            }
        }

        searchInstrumentalistBtn.setOnClickListener {

            if (instrumentalistStyle == 1) {
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                instrumentalistStyle = 0
            } else {

                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "instrumentalist")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "instrumentalist")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "instrumentalist")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "instrumentalist")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "instrumentalist")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "instrumentalist")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "instrumentalist")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "instrumentalist")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "instrumentalist")
                }

                instrumentalistStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                singerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                soundEngineerStyle = 0

            }
        }

        searchSoundEngineerBtn.setOnClickListener {

            if (soundEngineerStyle == 1) {
                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style1)
                allSkillsBtn.setBackgroundResource(R.drawable.style27)

                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "All")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "All")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "All")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "All")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "All")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "All")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "All")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "All")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "All")
                }

                soundEngineerStyle = 0
            } else {

                searchSoundEngineerBtn.setBackgroundResource(R.drawable.style27)
                allSkillsBtn.setBackgroundResource(R.drawable.style1)
                searchMusicProducerBtn.setBackgroundResource(R.drawable.style1)
                searchSingerBtn.setBackgroundResource(R.drawable.style1)
                searchVideoProductionBtn.setBackgroundResource(R.drawable.style1)
                searchRapperBtn.setBackgroundResource(R.drawable.style1)
                searchClothingDesignBtn.setBackgroundResource(R.drawable.style1)
                searchGraphicDesignerBtn.setBackgroundResource(R.drawable.style1)
                searchInstrumentalistBtn.setBackgroundResource(R.drawable.style1)


                if (allCitiesStyle == 1 || allCitiesStyle == 0) {
                    createRecyclerView("All", "sound_engineer")
                }
                if (londonStyle == 1) {
                    createRecyclerView("London", "sound_engineer")
                }
                if (bristolStyle == 1) {
                    createRecyclerView("Bristol", "sound_engineer")
                }
                if (birminghamStyle == 1) {
                    createRecyclerView("Birmingham", "sound_engineer")
                }
                if (manchesterStyle == 1) {
                    createRecyclerView("Manchester", "sound_engineer")
                }
                if (swanseaStyle == 1) {
                    createRecyclerView("Swansea", "sound_engineer")
                }
                if (cardiffStyle == 1) {
                    createRecyclerView("Cardiff", "sound_engineer")
                }
                if (dublinStyle == 1) {
                    createRecyclerView("Dublin", "sound_engineer")
                }
                if (edinburghStyle == 1) {
                    createRecyclerView("Edinburgh", "sound_engineer")
                }

                soundEngineerStyle = 1
                allSkillsStyle = 0
                musicProducerStyle = 0
                singerStyle = 0
                rapperStyle = 0
                videoProductionStyle = 0
                graphicDesignStyle = 0
                clothingDesignStyle = 0
                instrumentalistStyle = 0

            }

    }


    }


    private fun createRecyclerView(city: String, skill: String){

        val query = db.collection("users")

        val array = FirestoreArray(query, ClassSnapshotParser(UserModel::class.java))

        val options = FirestoreRecyclerOptions.Builder<UserModel>()
          .setSnapshotArray(array).setLifecycleOwner(this).build()

        recyclerView = findViewById(R.id.search_recyclerView)


        val adapter: FirestoreRecyclerAdapter<*, *> =
            SearchUsersAdapter(
                uid,
                array,
                options,
                this, city, skill
            )


        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }

    override fun onUserClick(user: UserModel, position: Int, buttonName: String){

        //Check button being clicked in recyclerView
        if (buttonName == "addFriendBtn"){
            val fullName = user.first_name + " " + user.last_name
            checkIfUsersAreFriends(user.uid, fullName, user.profile_image, user.city, user.email)
        }else if(buttonName == "showUserProfile"){
            val fullName = user.first_name + " " + user.last_name
            val intent = Intent(this, PopUpWindow::class.java)
            intent.putExtra("profileImage", user.profile_image)
            intent.putExtra("username", fullName)
            intent.putExtra("uid", user.uid)
            intent.putExtra("city", user.city)
            intent.putExtra("email", user.email)
            startActivity(intent)

        }

    }

    private fun checkIfUsersAreFriends(friendUID: String, friendName: String, profileImage: String,
                                       friendCity: String, friendEmail: String){

        val docRef = db.collection("users").document(uid).
            collection("friends").document(friendUID)

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val name = document.getString("fullName")

                if(name == friendName){
                    Toast.makeText(this, "You are already friends" , Toast.LENGTH_SHORT).show()
                }else{
                    checkRequest(friendUID, friendName, profileImage, friendCity, friendEmail)
                }


            } else {
                Log.d("doesn't exist", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }

    }

    private fun checkRequest(friendUID: String, friendName: String, profileImage: String, friendCity: String, friendEmail: String){

        val currentUserRequestRef = db.collection("users").document(uid)
            .collection("friend_requests").document(friendUID)

        val currentUserFriendsRef = db.collection("users").document(uid)
            .collection("friends").document(friendUID)

        val friendUser = hashMapOf(
            "uid" to friendUID,
            "fullName" to friendName,
            "profile_image" to profileImage,
            "city" to friendCity,
            "email" to friendEmail,
            "chat" to "false"
        )


        currentUserRequestRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")
                val trueOrFalse = document.getString("request_state")

                //If friend has sent you a request add to friends
                if (trueOrFalse == "true"){
                    addCurrentUserToFriendsDB(friendUID)

                    currentUserFriendsRef.set(friendUser).addOnSuccessListener {
                        Log.d(TAG, "Friend created for $uid")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                    Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show()
                }else{

                    //If current user does not have a request from that person send him a request
                    sendRequestToFriend(friendUID)

                    Toast.makeText(this, "Friend request sent", Toast.LENGTH_SHORT).show()
                }

            } else {
                Log.d("doesn't exist", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }




    }

    private fun sendRequestToFriend(friendUID: String){

        val receiverRequestRef = db.collection("users").document(friendUID)
            .collection("friend_requests").document(uid)

        val currentUserInfoRef = db.collection("users").document(uid)

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String


        currentUserInfoRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()
                val name  = "$firstName $lastName"
                profileImage = document.getString("profile_image").toString()
                city = document.getString("city").toString()
                email = document.getString("email").toString()

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to name,
                    "profile_image" to profileImage,
                    "city" to city,
                    "email" to email,
                    "request_state" to "true"
                )

                //Save request to receiver requests list
                receiverRequestRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                //Set notification
                //sendNotification(name)

            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }




    }

    private fun addCurrentUserToFriendsDB(friendUID: String){
        val receiverFriendsRef  = db.collection("users").document(friendUID)
            .collection("friends").document(uid)

        val docRef = db.collection("users").document(uid)

        var firstName: String
        var lastName: String
        var profileImage: String
        var city: String
        var email: String

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("exists", "DocumentSnapshot data: ${document.data}")

                firstName = document.getString("first_name").toString()
                lastName = document.getString("last_name").toString()
                val name  = "$firstName $lastName"
                profileImage = document.getString("profile_image").toString()
                city = document.getString("city").toString()
                email = document.getString("email").toString()

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to name,
                    "profile_image" to profileImage,
                    "city" to city,
                    "email" to email,
                    "chat" to "false"
                )

                //Save current user's info to other user friend's list
                receiverFriendsRef.set(user).addOnSuccessListener {
                    Log.d(TAG, "Friend created for $uid")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                deleteFriendRequests(friendUID)
            } else {
                Log.d("doesn't exist", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("error db", "get failed with ", exception)
            }


    }

    private fun deleteFriendRequests(friendUID: String){

        val currentUserRequestRef = db.collection("users").document(uid)
            .collection("friend_requests").document(friendUID)

        val receiverRequestRef = db.collection("users").document(friendUID)
            .collection("friend_requests").document(uid)

        currentUserRequestRef.delete()
        receiverRequestRef.delete()
    }

    private fun sendNotification(name: String){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)


                builder = Notification.Builder(this, channelID)
                    .setAutoCancel(true)
                    .setContentTitle("CollabWithMe")
                    .setContentText("New friend request from $name")
                    .setSmallIcon(R.drawable.app_logo_1_round_draw)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.app_logo_1_round_draw))
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setContentIntent(pendingIntent)
            }else{

                builder = Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("CollabWithMe")
                    .setContentText("Test dis ting")
                    .setSmallIcon(R.drawable.app_logo_1_round_draw)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.app_logo_1_round_draw))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234, builder.build())

    }

    //Go to homescreen when pressed back button
    override fun onBackPressed() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }


}


