import React from 'react';
import {Link} from "react-router-dom";

function BannerBox(props) {
    return (
        <>
            <div className="box bannerBox overflow-hidden rounded-lg group h-full">
                <Link to={'/'}>
                    <img src={props.img} alt="banner" className='w-full group-hover:scale-110 h-full'/>
                </Link>
            </div>
        </>
    );
}

export default BannerBox;