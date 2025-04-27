import React from 'react';
import Header from "../components/Header.jsx";
import Footer from "../components/Footer.jsx";
import Home from "../pages/user/Home.jsx"

function UserLayout(props) {
    return (
        <>
            <Header/>
            <Home/>
            <Footer/>
        </>
    );
}

export default UserLayout;