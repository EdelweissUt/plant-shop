import React from "react";
import "./Login.css"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFacebookF, faGoogle, faLinkedinIn, faTwitter} from "@fortawesome/free-brands-svg-icons";
function SignUpForm() {
    return (
        <form action="#" className="forms lsign-up-form">
            <h2 className="ltitle">Sign up</h2>
            <div className="linput-field">
                <i className="fas fa-user"></i>
                <input type="text" placeholder="Username" />
            </div>
            <div className="linput-field">
                <i className="fas fa-envelope"></i>
                <input type="email" placeholder="Email" />
            </div>
            <div className="linput-field">
                <i className="fas fa-lock"></i>
                <input type="password" placeholder="Password" />
            </div>
            <input type="submit" className="lbtn" value="Sign up" />
            <p className="lsocial-text">Or Sign up with social platforms</p>
            <div className="lsocial-media flex justify-center space-x-4">
                <a href="#" className="lsocial-icon p-4 mx-2 border border-gray-400 rounded-full bg-blue-600 hover:bg-blue-700 transition-colors duration-300">
                    <FontAwesomeIcon icon={faFacebookF} className="text-white hover:text-yellow-300 transition-colors duration-300" />
                </a>
                <a href="#" className="lsocial-icon p-4 mx-2 border border-gray-400 rounded-full bg-blue-400 hover:bg-blue-500 transition-colors duration-300">
                    <FontAwesomeIcon icon={faTwitter} className="text-white hover:text-yellow-300 transition-colors duration-300" />
                </a>
                <a href="#" className="lsocial-icon p-4 mx-2 border border-gray-400 rounded-full bg-red-500 hover:bg-red-600 transition-colors duration-300">
                    <FontAwesomeIcon icon={faGoogle} className="text-white hover:text-yellow-300 transition-colors duration-300" />
                </a>
                <a href="#" className="lsocial-icon p-4 mx-2 border border-gray-400 rounded-full bg-blue-800 hover:bg-blue-900 transition-colors duration-300">
                    <FontAwesomeIcon icon={faLinkedinIn} className="text-white hover:text-yellow-300 transition-colors duration-300" />
                </a>
            </div>


        </form>
    );
}

export default SignUpForm;
