
package com.example.selltez.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.selltez.R
import com.example.selltez.fragments.*
import com.example.selltez.storage.Communicator
import com.example.selltez.storage.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_file.view.*
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(),Communicator {
    lateinit var toogle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
    lateinit var drawer_layout: DrawerLayout
    lateinit var navigationView:NavigationView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var preferences: SharedPreferences
    private var doubleBackToExitPressedOnce = false





    val homeFragment = HomeFragment()
    val complaintFragment = ComplaintFragment()
    val notificatiomfragment = NotificationFragment()
    val accountFragment = AccountFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val name  = preferences.getString("name", null)!!
        val num  = preferences.getString("mobile", null)!!


        toolbar = findViewById(R.id.toolbar)
        drawer_layout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.nav_one)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        currentFragment(complaintFragment)

//        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
//        val nam = preferences.getString("name", null)!!
//        val num = preferences.getString("mobile",null)!!
//        val photoid = preferences.getString("proof",null)!!




        try {

            setSupportActionBar(toolbar)
        }catch(e: Exception){

            supportActionBar?.hide()
        }


        toogle = ActionBarDrawerToggle(this,drawer_layout,toolbar, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)
       // supportActionBar?.setDisplayHomeAsUpEnabled(true)


       // navigationView.nav_name.text = "hello"

        val view: View = navigationView.getHeaderView(0)
        val headname = view.findViewById<TextView>(R.id.nav_name)
        val headnum = view.findViewById<TextView>(R.id.nav_number)
        headnum.text = num
        headname.text = name
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mchange_pwd -> {
                   // Toast.makeText(applicationContext, "change password", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, ChangePasswordActivity::class.java)
                    startActivity(i)
                    drawer_layout.closeDrawer(GravityCompat.START)
                }

                R.id.mlogout -> {
                    // Toast.makeText(applicationContext, "logout", Toast.LENGTH_SHORT).show()
                    preferences = this.getSharedPreferences(
                        SharedPrefManager.SHARED_PREF_NAME,
                        Context.MODE_PRIVATE
                    )
                    val editor = preferences.edit()
                    editor.clear()
                    editor.apply()
                    drawer_layout.closeDrawer(GravityCompat.START)
                    val intent = Intent(applicationContext, Login_Acti::class.java)
                    startActivity(intent)
                }
            }
            true
        }

       bottomNavigationView.setOnNavigationItemSelectedListener(mBottomnavigation)
    }


//    override fun onStart() {
//        super.onStart()
//
//        if(!SharedPrefManager.getInstance(this).isLoggedIn){
//            val intent = Intent(applicationContext, Login_Acti::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//    }

//    override fun onStart() {
//        super.onStart()
//
//        if(SharedPrefManager.getInstance(this).isLoggedIn){
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//            startActivity(intent)
//        }
//    }
    val mBottomnavigation = BottomNavigationView.OnNavigationItemSelectedListener { menuitem:MenuItem->
       when(menuitem.itemId){
//           R.id.ic_home ->{
//              //  Toast.makeText(this,"home",Toast.LENGTH_LONG).show()
//                currentFragment(homeFragment)
//            }
            R.id.ic_complaint ->{
               // Toast.makeText(this,"complaint",Toast.LENGTH_LONG).show()
                currentFragment(complaintFragment)
            }
           R.id.ic_notificatons ->{
              //  Toast.makeText(this,"notification",Toast.LENGTH_LONG).show()
                currentFragment(notificatiomfragment)
            }
            R.id.ic_account ->{
               // Toast.makeText(this,"account",Toast.LENGTH_LONG).show()
                currentFragment(accountFragment)
            }

        }

        return@OnNavigationItemSelectedListener true
    }

      fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame_2, fragment)
            commit()
        }
    }
    override fun onBackPressed() {



        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            moveTaskToBack(true)
            Process.killProcess(Process.myPid())
            System.exit(1)
            return
        }

            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "click BACK again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

        if (drawer_layout != null){

            if(drawer_layout.isDrawerOpen(GravityCompat.START)){
                drawer_layout.closeDrawer(GravityCompat.START)
            }



        }

        }





       // }
    //}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
        }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, Login_Acti::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun passData(
        datetime: String,
        againstname: String,
        descriptionn: String,
        imageuurl: String
    ) {
        val bundle = Bundle()
        bundle.putString("dt",datetime)
        bundle.putString("an",againstname)
        bundle.putString("dess",descriptionn)
        bundle.putString("img",imageuurl)

        val transition = this.supportFragmentManager.beginTransaction()
        val fragmentdetails = recyclerdetails()
        fragmentdetails.arguments = bundle
        transition.replace(R.id.content_frame_2,fragmentdetails).commit()
    }
}
















