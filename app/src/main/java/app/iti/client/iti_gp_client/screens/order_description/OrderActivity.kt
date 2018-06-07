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
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.screens.payment.PaymentActivity
import kotlinx.android.synthetic.main.activity_order.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.io.Serializable


/**
 * Displays Order Screen
 */

class OrderActivity : AppCompatActivity(), View {
    // reference to order presenter
    private var mPresenter:Presenter?=null
    // the number of image to be removed
    private var removedImageNumber:Int?= null
    // array of order images
    private var imageArray: ArrayList<Bitmap>? = null
    // array to save image Paths
    private var imagePaths: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        // initialize image array and paths array
        imageArray = ArrayList()
        imagePaths = ArrayList()

        // initialize presenter
        mPresenter = OrderPresenter()
        mPresenter?.initPresenter(this)
        //initialing all image views with null to handle updating them when adding new image view
        setAllImageViewsNull()
        // all the set on long click listeners for all added images
        deleteImage()

    }

    // action when upload image clicked
    fun getImageToUpload(view:android.view.View){
        Log.i("OrderActivity","get button clicked!")
        // call method from presenter to show dialog for user to choose image from gallery or camera
        mPresenter?.getImageAction()
//        order_imageScroller.enable
    }


    // update imageView after getting image from gallery or from camera
    override fun updateImageView(image: Bitmap,path: String, counter:Int) {
        if(counter == 1) {
            order_image1.setImageBitmap(image)
            order_image1.visibility = android.view.View.VISIBLE
            imageArray!!.add(0,image)
            imagePaths!!.add(0,path)
        }else if(counter == 2){
            order_image2.setImageBitmap(image)
            order_image2.visibility = android.view.View.VISIBLE
            imageArray!!.add(1,image)
            imagePaths!!.add(1,path)
        }else if(counter == 3){
            order_image3.setImageBitmap(image)
            order_image3.visibility = android.view.View.VISIBLE
            imageArray!!.add(2,image)
            imagePaths!!.add(2,path)

        }else if(counter == 4){
            order_image4.setImageBitmap(image)
            order_image4.visibility = android.view.View.VISIBLE
            imageArray!!.add(3,image)
            imagePaths!!.add(3,path)
        }else if(counter == 5 && (order_image5.drawable as BitmapDrawable).bitmap == null){
            order_image5.setImageBitmap(image)
            order_image5.visibility = android.view.View.VISIBLE
            imageArray!!.add(4,image)
            imagePaths!!.add(4,path)
        }else{
            Toast.makeText(this,"max number of images", Toast.LENGTH_SHORT).show()
        }
    }

    // method to get images after coming back to the activity
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

    // set all Long click listeners for all image views to remove them
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

    // count all empty image views to use this count when removing image cases
    fun checkImageViewEmpty() : Int{
        var count = 0
        if( (order_image1.drawable as BitmapDrawable).bitmap == null){
            Log.i("image1"," null")
            order_image1.visibility = android.view.View.GONE
            count++
        }
        if( (order_image2.drawable as BitmapDrawable).bitmap == null){
            Log.i("image2"," null")
            order_image2.visibility = android.view.View.GONE
            count++
        }
        if( (order_image3.drawable as BitmapDrawable).bitmap == null){
            Log.i("image3"," null")
            order_image3.visibility = android.view.View.GONE
            count++
        }
        if( (order_image4.drawable as BitmapDrawable).bitmap == null){
            Log.i("image4"," null")
            order_image4.visibility = android.view.View.GONE
            count++
        }
        if( (order_image5.drawable as BitmapDrawable).bitmap == null){
            Log.i("image5"," null")
            order_image5.visibility = android.view.View.GONE
            count++
        }
        return count
    }

     /*method to shift images into imageViews after removing
            this method contains all removing cases*/
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
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
            }else if(removedImageNum==1 && notNullViews == 1){
                // just empty 1
                order_image1.setImageBitmap(null)
                order_image1.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
            }else if(removedImageNum==1 && notNullViews == 2){
                //  2 -> 1
                val bitmapImage2 = (order_image2.drawable as BitmapDrawable).bitmap
                order_image1.setImageBitmap(bitmapImage2)
                // empty 2
                order_image2.setImageBitmap(null)
                order_image2.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
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
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
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
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
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
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
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
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
            }else if(removedImageNum ==4){
                //  5 -> 4
                val bitmapImage5 = (order_image5.drawable as BitmapDrawable).bitmap
                order_image4.setImageBitmap(bitmapImage5)
                // make fifth imageView empty
                order_image5.setImageBitmap(null)
                order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
            }else if(removedImageNum == notNullViews ){
             // this means that the user removed the last image then there is no shifting
             // make fifth imageView empty
                 order_image5.setImageBitmap(null)
                 order_image5.visibility = android.view.View.GONE
                mPresenter?.decrementCounter()
                imageArray!!.removeAt(removedImageNum-1)
                imagePaths!!.removeAt(removedImageNum-1)
             }

        val check = checkImageViewEmpty()
        Log.i("num of null after", check.toString())
        Log.i("ImageCounter",mPresenter?.imageCounter!!.toString())
         }

    // set all image views with null bitmap
    fun setAllImageViewsNull(){
        order_image1.setImageBitmap(null)
        order_image2.setImageBitmap(null)
        order_image3.setImageBitmap(null)
        order_image4.setImageBitmap(null)
        order_image5.setImageBitmap(null)
    }

    // method to go to PaymentActivity
    fun goToPayment(view: android.view.View){
        val intent = Intent(this, PaymentActivity::class.java)
        // get data from screan
        val title = order_titleEditText.text.toString()
        val description = order_descriptionEditText.text.toString()
        if(title != "" && description != "" && imageArray!!.size>0) {
                // create order object to send it to the payment activity
                val order = Order(title, description, imagePaths!!)
                intent.putExtra("Order", order as Serializable)
                startActivity(intent)
        }else{
            Toast.makeText(this,"please enter the full information about your order (title, description and one image at least",Toast.LENGTH_LONG).show()
        }

    }

}

