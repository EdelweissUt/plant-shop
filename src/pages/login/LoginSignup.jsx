import React, { useState } from "react";
import SignInForm from "./SignInForm.jsx";
import SignUpForm from "./SignUpForm.jsx";
import Panel from "./Panel.jsx";
import "./Login.css"

function LoginSignup() {

    const [isSignUp, setIsSignUp] = useState(false);

    const toggleForm = () => {
        setIsSignUp(!isSignUp);
    };

    return (
        <div className={`lcontainer ${isSignUp ? "lsign-up-mode" : ""}`}>
            <div className="lforms-container">
                <div className="lsignin-signup">
                    {isSignUp ? <SignUpForm /> : <SignInForm />}
                </div>
            </div>

            <div className="lpanels-container">
                <Panel toggleForm={toggleForm} />
            </div>
        </div>
    );
}

export default LoginSignup;
