import React from "react";
import "./Login.css"
import signinImage from "../../assets/images/signin.png";
import signupImage from "../../assets/images/signup.png";
function Panel({ toggleForm }) {
    return (
        <>
            <div className="lpanel lleft-panel">
                <div className="lcontent">
                    <h2 className="mt-16 mb-16 text-lg text-center font-medium leading-relaxed">
                        Hãy cùng bắt đầu hành trình gieo trồng những mầm xanh đầu tiên – tạo nên góc sống xanh tươi và bình yên dành riêng cho bạn.
                    </h2>
                    <button className="lbtn transparent" onClick={() => toggleForm(true)}>
                        Sign up
                    </button>
                </div>
                <img
                    src={signinImage}
                    className="limage"
                    alt=""
                />
            </div>
            <div className="lpanel lright-panel">
                <div className="lcontent">
                    <h2 className="mt-6 mb-6 text-lg text-center font-medium leading-relaxed">
                        Chào mừng bạn quay trở lại khu vườn nhỏ của chúng tôi – nơi mỗi chậu cây là một niềm vui đang chờ bạn chăm sóc và nâng niu mỗi ngày.
                    </h2>

                    <button className="lbtn transparent" onClick={() => toggleForm(false)}>
                        Sign in
                    </button>
                </div>
                <img
                    src={signupImage}
                    className="limage"
                    alt=""
                />
            </div>
        </>
    );
}

export default Panel;
