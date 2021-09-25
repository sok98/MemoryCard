package com.yeseul.memorycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yeseul.memorycard.databinding.ActivityMainBinding
import com.yeseul.memorycard.presentation.wordlist.WordListFragment
import com.yeseul.memorycard.presentation.shareword.ShareWordFragment
import com.yeseul.memorycard.presentation.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wordListFragment = WordListFragment()
        val shareWordFragment = ShareWordFragment()
        val myPageFragment = MyPageFragment()

        replaceFragment(wordListFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_wordList -> replaceFragment(wordListFragment)
                R.id.bottom_shareWord -> replaceFragment(shareWordFragment)
                R.id.bottom_myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }


}