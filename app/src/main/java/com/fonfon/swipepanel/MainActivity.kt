package com.fonfon.swipepanel

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fonfon.swipepanel.FullscreenManager
import java.util.*
import com.fonfon.swipepanel.R
import com.fonfon.swipepanel.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

private val random = Random()

@SuppressLint("StaticFieldLeak")
private val fullscreenManager = FullscreenManager()

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    fullscreenManager.setFullscreenView(content)
    fullscreenManager.listener = {
      if (it) {
        frame_top.visibility = View.GONE
      } else {
        frame_top.visibility = View.VISIBLE
      }
    }
    pager.adapter = PagerAdapter(supportFragmentManager)
    supportFragmentManager.beginTransaction().replace(R.id.second_fragment_view, ListFragment()).commit()
    button.setOnClickListener {
      Toast.makeText(this, "ТЫК", Toast.LENGTH_SHORT).show()
    }
    frame.interceptor = pager
    frame.onClickListener = View.OnClickListener {
      fullscreenManager.toggle()
    }
  }
}

class PagerAdapter(public val fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
  override fun getItem(position: Int) = PageFragment()
  override fun getCount() = 60
}

class PageFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment, container, false)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
    view.setOnClickListener {
      fullscreenManager.toggle()
    }
  }
}

class ListFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_list, container, false)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycler.adapter = Adapter()
  }
}

class Adapter : RecyclerView.Adapter<Adapter.Holder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
      LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
  )
  override fun getItemCount() = 600
  override fun onBindViewHolder(holder: Holder, position: Int) {}

  inner class Holder(v: View) : RecyclerView.ViewHolder(v)
}
