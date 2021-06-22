package kate.tutorial.turtlesoup.puzzle

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import kate.tutorial.turtlesoup.R
import kate.tutorial.turtlesoup.databinding.ActivityPostPuzzleBinding

class PuzzlePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostPuzzleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostPuzzleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_post_puzzle, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_done -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment
                if (fragment is PuzzlePostFragment) {
                    fragment.postPuzzle()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
