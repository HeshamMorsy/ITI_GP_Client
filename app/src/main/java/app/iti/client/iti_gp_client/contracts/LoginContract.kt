package app.iti.client.iti_gp_client.contracts

/**
 * Created by Hesham on 5/29/2018.
 * define the contract between the view LoginActivity, the model LoginModel and the presenter LoginPresenter
 */
interface LoginContract {
    interface Model {
        fun checkEmailAndPassword(email: String, password: String)
    }

    interface View {
//
    }

    interface Presenter {
        fun initPresenter(view: View)
        fun login(email: String, password: String)
    }
}