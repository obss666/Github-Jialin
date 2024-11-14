package com.example.github_jialin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.activity.showActivity.RepositoriesActivity
import com.example.github_jialin.ui.activity.showActivity.UsersActivity
import com.example.github_jialin.utils.LogUtil
import com.example.github_jialin.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SearchActivity111111"
    }

    private val mViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    private lateinit var mBackButton: Button
    private lateinit var mSearchEditText: EditText
    private lateinit var mClearSearchButton: Button
    private lateinit var mSearchResultLayout: LinearLayout
    private lateinit var mSearchRepoLayout: LinearLayout
    private lateinit var mRepoImage: ImageView
    private lateinit var mRepoInfoText: TextView
    private lateinit var mSearchUserLayout: LinearLayout
    private lateinit var mUserImage: ImageView
    private lateinit var mUserInfoText: TextView
    private lateinit var mSearchRecordLayout: LinearLayout
    private lateinit var mDeleteSearchRecordButton: Button
    private lateinit var mSearchRecordListView: ListView
    private lateinit var mFindValueTextView: TextView
    private lateinit var searchRecordAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mViewModel.searchRecordList = mViewModel.getSearchRecords(ClientApplication.USERNAME)

        mBackButton = findViewById(R.id.searchBackButton)
        mSearchEditText = findViewById(R.id.searchEditText)
        mClearSearchButton = findViewById(R.id.clearSearchButton)
        mSearchResultLayout = findViewById(R.id.searchResultLayout)
        mSearchRepoLayout = findViewById(R.id.searchRepoLayout)
        mRepoImage = findViewById(R.id.repoImage)
        mRepoInfoText = findViewById(R.id.repoInfoText)
        mSearchUserLayout = findViewById(R.id.searchUserLayout)
        mUserImage = findViewById(R.id.userImage)
        mUserInfoText = findViewById(R.id.userInfoText)
        mSearchRecordLayout = findViewById(R.id.searchRecordLayout)
        mDeleteSearchRecordButton = findViewById(R.id.deleteSearchRecordButton)
        mSearchRecordListView = findViewById(R.id.searchRecordListView)
        mFindValueTextView = findViewById(R.id.findValueTextView)

        // 搜索记录列表
        searchRecordAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, mViewModel.searchRecordList)
        mSearchRecordListView.adapter = searchRecordAdapter

        mBackButton.setOnClickListener {
            finish()
        }

        mClearSearchButton.setOnClickListener {
            mSearchEditText.setText("")
        }

        mDeleteSearchRecordButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("确认删除")
            alertDialogBuilder.setMessage("您确定要删除搜索记录吗？")
            alertDialogBuilder.setPositiveButton("确定") { dialog, which ->
                mViewModel.deleteSearchRecords(ClientApplication.USERNAME)
                mViewModel.searchRecordList.clear()
                searchRecordAdapter.notifyDataSetChanged()
                checkShow(mSearchEditText.text.toString())
            }
            alertDialogBuilder.setNegativeButton("取消", null)
            alertDialogBuilder.show()
        }

        mSearchRecordListView.setOnItemClickListener{ _, _, position, _ ->
            val content = mViewModel.searchRecordList[position]
            mSearchEditText.setText(content)
            mSearchEditText.setSelection(content.length)
        }

        mSearchRepoLayout.setOnClickListener {
            val content = mSearchEditText.text.toString().trim()
            mViewModel.saveSearchRecords(ClientApplication.USERNAME, content)
            val intent = Intent(this, RepositoriesActivity::class.java)
            intent.putExtra("content", content)
            startActivity(intent)
        }

        mSearchUserLayout.setOnClickListener {
            val content = mSearchEditText.text.toString().trim()
            mViewModel.saveSearchRecords(ClientApplication.USERNAME, content)
            val intent = Intent(this, UsersActivity::class.java)
            intent.putExtra("content", content)
            startActivity(intent)
        }

        mSearchEditText.addTextChangedListener {
            val context = it.toString().trim()
            mRepoImage.setImageResource(R.drawable.ic_repo_black)
            mRepoInfoText.text = "包含\"$context\"的仓库"
            mUserImage.setImageResource(R.drawable.ic_person_black)
            mUserInfoText.text = "包含\"$context\"的人员"
            checkShow(context)
        }
    }

    override fun onResume() {
        super.onResume()
        mSearchEditText.requestFocus()
        mViewModel.searchRecordList.clear()
        mViewModel.searchRecordList.addAll(mViewModel.getSearchRecords(ClientApplication.USERNAME))
        searchRecordAdapter.notifyDataSetChanged()
        LogUtil.d(TAG, "searchRecordList = ${mViewModel.searchRecordList}")
        checkShow(mSearchEditText.text.toString())
    }

    private fun checkShow(context: String) {
        if (context.isNotBlank()) {
            mSearchResultLayout.visibility = LinearLayout.VISIBLE
            mFindValueTextView.visibility = TextView.GONE
            mSearchRecordLayout.visibility = LinearLayout.GONE
        } else {
            mSearchResultLayout.visibility = LinearLayout.GONE
            if(mViewModel.searchRecordList.isNotEmpty()) {
                mSearchRecordLayout.visibility = LinearLayout.VISIBLE
                mFindValueTextView.visibility = TextView.GONE
            } else {
                mSearchRecordLayout.visibility = LinearLayout.GONE
                mFindValueTextView.visibility = TextView.VISIBLE
            }
        }
    }
}