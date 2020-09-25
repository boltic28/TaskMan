package com.boltic28.taskmanager.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.signin.FireUserManager

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "mainActivity_test"
    }

    private val model: MainActivityModel by lazy { ViewModelProviders.of(this).get(
        MainActivityModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.userManager = FireUserManager.getInstance(this)

        if (model.userManager.userIn.id.isEmpty()){
            toSingFragment()
        }else{
            toMainFragment()
        }

    }

    fun toSingFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,
                SignFragment(),
                SignFragment.FR_TAG)
            .commit()
    }

    fun toMainFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,
                MainFragment(),
                MainFragment.TAG)
            .commit()
    }

    fun setToolBarName(name: String){

    }

    override fun onStart() {
        super.onStart()
//        updateUI(userManager.mUser)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out -> {
                model.userManager.signOut()
                return true
            }
        }
        return false
    }
}

//        if (mUser == null) {
//            startSignActivity()
//            finish()
//        } else {
//            mUsername = mUser!!.displayName
//            if (mUser!!.photoUrl != null) {
//                mPhotoUrl = mUser!!.photoUrl.toString()
//            }
//        }

//class MessageViewHolder(v: View?) : ViewHolder(v!!) {
//    var messageTextView: TextView =
//        itemView.findViewById<View>(R.id.messageTextView) as TextView
//    var messageImageView: ImageView =
//        itemView.findViewById<View>(R.id.messageImageView) as ImageView
//    var messengerTextView: TextView =
//        itemView.findViewById<View>(R.id.messengerTextView) as TextView
//
//}

////-----------
//
//mLinearLayoutManager = LinearLayoutManager(this)
//mLinearLayoutManager.stackFromEnd = true
//messageRecyclerView.layoutManager = mLinearLayoutManager
//
//mFirebaseDatabaseReference = FirebaseDatabase.getInstance().reference
//val parser: SnapshotParser<FriendlyMessage> =
//    SnapshotParser { dataSnapshot ->
//        val friendlyMessage: FriendlyMessage =
//            dataSnapshot.getValue<FriendlyMessage>(FriendlyMessage::class.java)!!
//        friendlyMessage.copy(id = dataSnapshot.key!!)
//        friendlyMessage
//    }
//
//val messagesRef =
//    mFirebaseDatabaseReference!!.child(MainActivity.MESSAGES_CHILD)
//val options: FirebaseRecyclerOptions<FriendlyMessage> =
//    FirebaseRecyclerOptions.Builder<FriendlyMessage>()
//        .setQuery(messagesRef, parser)
//        .build()
//
//mFirebaseAdapter = object :
//    FirebaseRecyclerAdapter<FriendlyMessage, MainActivity.MessageViewHolder>(options) {
//    override fun onCreateViewHolder(
//        viewGroup: ViewGroup,
//        i: Int
//    ): MainActivity.MessageViewHolder {
//        val inflater = LayoutInflater.from(viewGroup.context)
//        return MainActivity.MessageViewHolder(
//            inflater.inflate(R.layout.item_row, viewGroup, false)
//        )
//    }
//
//    override fun onBindViewHolder(
//        viewHolder: MainActivity.MessageViewHolder,
//        position: Int,
//        friendlyMessage: FriendlyMessage
//    ) {
//        progressBar.visibility = ProgressBar.INVISIBLE
//        if (friendlyMessage.text != null) {
//            viewHolder.messageTextView.text = friendlyMessage.text
//            viewHolder.messageTextView.visibility = TextView.VISIBLE
//            viewHolder.messageImageView.visibility = ImageView.GONE
//        } else if (friendlyMessage.imageUrl != null) {
//            val imageUrl: String = friendlyMessage.imageUrl
//            if (imageUrl.startsWith("gs://")) {
//                val storageReference: StorageReference = FirebaseStorage.getInstance()
//                    .getReferenceFromUrl(imageUrl)
//                storageReference.downloadUrl.addOnCompleteListener(
//                    OnCompleteListener<Uri> { task ->
//                        if (task.isSuccessful) {
//                            val downloadUrl = task.result.toString()
//                            Glide.with(viewHolder.messageImageView.context)
//                                .load(downloadUrl)
//                                .into(viewHolder.messageImageView)
//                        } else {
//                            Log.w(
//                                MainActivity.TAG,
//                                "Getting download url was not successful.",
//                                task.exception
//                            )
//                        }
//                    })
//            } else {
//                Glide.with(viewHolder.messageImageView.context)
//                    .load(friendlyMessage.imageUrl)
//                    .into(viewHolder.messageImageView)
//            }
//            viewHolder.messageImageView.visibility = ImageView.VISIBLE
//            viewHolder.messageTextView.visibility = TextView.GONE
//        }
//        viewHolder.messengerTextView.text = friendlyMessage.name
//    }
//}
//
//mFirebaseAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
//    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//        super.onItemRangeInserted(positionStart, itemCount)
//        val friendlyMessageCount: Int = mFirebaseAdapter.getItemCount()
//        val lastVisiblePosition: Int =
//            mLinearLayoutManager.findLastCompletelyVisibleItemPosition()
//        // If the recycler view is initially being loaded or the
//        // user is at the bottom of the list, scroll to the bottom
//        // of the list to show the newly added message.
//        if (lastVisiblePosition == -1 ||
//            positionStart >= friendlyMessageCount - 1 &&
//            lastVisiblePosition == positionStart - 1
//        ) {
//            messageRecyclerView.scrollToPosition(positionStart)
//        }
//    }
//}
//)
//
//messageRecyclerView.adapter = mFirebaseAdapter
//
////-----------