package app.iti.client.iti_gp_client.screens.order_description

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.OrderContract.Presenter
import app.iti.client.iti_gp_client.contracts.OrderContract.View
import kotlinx.android.synthetic.main.activity_order.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


/**
 * Displays Order Screen
 */

class OrderActivity : AppCompatActivity(), View {
    // reference to order presenter
    var mPresenter:Presenter?=null
    // counter of images in scroll view
    var count = 0
    var removedImageNumber:Int?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        // initialize presenter
        mPresenter = OrderPresenter()
        mPresenter?.initPresenter(this)
        setAllImageViewsNull()
        deleteImage()

    }

   /* private fun handleNestedScrollViews() {
        order_outer.setOnTouchListener { v, event ->  order_inner.getParent()
                .requestDisallowInterceptTouchEvent(false)
            false}

        order_inner.setOnTouchListener { v, event ->  v.getParent()
                .requestDisallowInterceptTouchEvent(true)
            false}

    }*/

    // action when upload image clicked
    fun getImageToUpload(view:android.view.View){
        Log.i("OrderActivity","get button clicked!")
        // call method from presenter to show dialog for user to choose image from gallery or camera
        mPresenter?.getImageAction()
//        order_imageScroller.enable
    }


    // update imageView after getting image from gallery or from camera
    override fun updateImageView(image: Bitmap, counter:Int) {
        count = counter
        if(counter == 1) {
            order_image1.setImageBitmap(image)
            order_image1.visibility = android.view.View.VISIBLE
        }else if(counter == 2){
            order_image2.setImageBitmap(image)
            order_image2.visibility = android.view.View.VISIBLE
        }else if(counter == 3){
            order_image3.setImageBitmap(image)
            order_image3.visibility = android.view.View.VISIBLE
        }else if(counter == 4){
            order_image4.setImageBitmap(image)
            order_image4.visibility = android.view.View.VISIBLE
        }else if(counter == 5 && (order_image5.drawable as BitmapDrawable).bitmap == null){
            order_image5.setImageBitmap(image)
            order_image5.visibility = android.view.View.VISIBLE
        }else{
            Toast.makeText(this,"max number of images", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("request",requestCode.toString())
        Log.i("result",resultCode.toString())
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                //Handle the images
                mPresenter?.convertFilesToBitmap(imagesFiles)
            }
        })

    }

    fun deleteImage(){
        order_image1.setOnLongClickListener {
            removedImageNumber = 1
            val numOfNullViews = checkImageViewEmpty()
            shiftImages(removedImageNumber!!,numOfNullViews)
//            order_image1.setImageBitmap(null)
            true }
        order_image2.setOnLongClickListener {
//            order_image2.setImageBitmap(null)
            removedImageNumber =2
            val numOfNullViews = checkImageViewEmpty()
            shiftImages(removedImageNumber!!,numOfNullViews)
            true }
        order_image3.setOnLongClickListener {
//            order_image3.setImageBitmap(null)
            removedImageNumber =3
            val numOfNullViews = checkImageViewEmpty()
            shiftImages(removedImageNumber!!,numOfNullViews)
            true }
        order_image4.setOnLongClickListener {
//            order_image4.setImageBitmap(null)
            removedImageNumber =4
            val numOfNullViews = checkImageViewEmpty()
            shiftImages(removedImageNumber!!,numOfNullViews)
            true }
        order_image5.setOnLongClickListener {
//            order_image5.setImageBitmap(null)
            removedImageNumber =5
            val numOfNullViews = checkImageViewEmpty()
            shiftImages(removedImageNumber!!,numOfNullViews)
            true }

    }

    fun checkImageViewEmpty() : Int{
        count = 0
        if( (order_image1.drawable as BitmapDrawable).bitmap == null){
            Log.i("image1"," null")
            count++
        }
        if( (order_image2.drawable as BitmapDrawable).bitmap == null){
            Log.i("image2"," null")
            count++
        }
        if( (order_image3.drawable as BitmapDrawable).bitmap == null){
            Log.i("image3"," null")
            count++
        }
        if( (order_image4.drawable as BitmapDrawable).bitmap == null){
            Log.i("image4"," null")
            count++
        }
        if( (order_image5.drawable as BitmapDrawable).bitmap == null){
            Log.i("image5"," null")
            count++
        }
        return count
    }

    // method to shift images into imageViews after removing
    fun shiftImages(removedImageNum: Int, numOfNullViews: Int){
        val notNullViews = 5 - numOfNullViews
        Log.i("num of Null before",numOfNullViews.toString())
        // shift all bitmaps to the empty imageView
            if(removedImageNum==1 && notNullViews == 5){
                //  2 -> 1
                val bitmapImage2 = (order_image2.drawable as BitmapDrawable).bitmap
                order_image1.setImageBitmap(bitmapImage2)
                //  3 -> 2
                val bitmapImage3 = (order_image3.drawable as BitmapDrawable).bitmap
                order_image2.setImageBitmap(bitmapImage3)
                //  4 -> 3
                val bitmapImage4 = (order_image4.drawable as BitmapDrawable).bitmap
                order_image3.setImageBitmap(bitmapImage4)
                //  5 -> 4
                val bitmapImage5 = (order_image5.drawable as BitmapDrawable).bitmap
                order_image4.setImageBitmap(bitmapImage5)
                // empty 5
                order_image5.setImageBitmap(null)
                order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum==1 && notNullViews == 1){
                // just empty 1
                order_image1.setImageBitmap(null)
                order_image1.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum==1 && notNullViews == 2){
                //  2 -> 1
                val bitmapImage2 = (order_image2.drawable as BitmapDrawable).bitmap
                order_image1.setImageBitmap(bitmapImage2)
                // empty 2
                order_image2.setImageBitmap(null)
                order_image2.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum==1 && notNullViews == 3){
                //  2 -> 1
                val bitmapImage2 = (order_image2.drawable as BitmapDrawable).bitmap
                order_image1.setImageBitmap(bitmapImage2)
                //  3 -> 2
                val bitmapImage3 = (order_image3.drawable as BitmapDrawable).bitmap
                order_image2.setImageBitmap(bitmapImage3)
                // empty 3
                order_image3.setImageBitmap(null)
                order_image3.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum==1 && notNullViews == 4){
                //  2 -> 1
                val bitmapImage2 = (order_image2.drawable as BitmapDrawable).bitmap
                order_image1.setImageBitmap(bitmapImage2)
                //  3 -> 2
                val bitmapImage3 = (order_image3.drawable as BitmapDrawable).bitmap
                order_image2.setImageBitmap(bitmapImage3)
                //  4 -> 3
                val bitmapImage4 = (order_image4.drawable as BitmapDrawable).bitmap
                order_image3.setImageBitmap(bitmapImage4)
                // empty 4
                order_image4.setImageBitmap(null)
                order_image4.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum ==2 ){
                //  3 -> 2
                val bitmapImage3 = (order_image3.drawable as BitmapDrawable).bitmap
                order_image2.setImageBitmap(bitmapImage3)
                //  4 -> 3
                val bitmapImage4 = (order_image4.drawable as BitmapDrawable).bitmap
                order_image3.setImageBitmap(bitmapImage4)
                //  5 -> 4
                val bitmapImage5 = (order_image5.drawable as BitmapDrawable).bitmap
                order_image4.setImageBitmap(bitmapImage5)
                // empty 5
                order_image5.setImageBitmap(null)
                order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum ==3){
                //  4 -> 3
                val bitmapImage4 = (order_image4.drawable as BitmapDrawable).bitmap
                order_image3.setImageBitmap(bitmapImage4)
                //  5 -> 4
                val bitmapImage5 = (order_image5.drawable as BitmapDrawable).bitmap
                order_image4.setImageBitmap(bitmapImage5)
                // empty 5
                order_image5.setImageBitmap(null)
                order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }else if(removedImageNum ==4){
                //  5 -> 4
                val bitmapImage5 = (order_image5.drawable as BitmapDrawable).bitmap
                order_image4.setImageBitmap(bitmapImage5)
                // make fifth imageView empty
                order_image5.setImageBitmap(null)
                order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
            }

        else if(removedImageNum == notNullViews ){
             // this means that the user removed the last image then there is no shifting
             // make fifth imageView empty
                 order_image5.setImageBitmap(null)
                 order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
             }

        val check = checkImageViewEmpty()
        Log.i("num of null after", check.toString())
        Log.i("ImageCounter",mPresenter?.imageCounter!!.toString())
         }


    fun setAllImageViewsNull(){
        order_image1.setImageBitmap(null)
        order_image2.setImageBitmap(null)
        order_image3.setImageBitmap(null)
        order_image4.setImageBitmap(null)
        order_image5.setImageBitmap(null)
    }

    fun goToPayment(view: android.view.View){
//        var intent = Intent
    }
}

