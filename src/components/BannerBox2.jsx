import React from 'react'
import {Link} from "react-router-dom";

const BannerBoxV2 = (props) => {
    return (
        <><div className='relative group'>

            <div className="bannerBoxV2 w-full overflow-hidden rounded-md ">
                <img
                    src={props.image} className="w-full transition-all duration-150 group-hover:scale-105"/>
            </div>
            <div
                className={`info absolute p-5 top-0 ${
                    props.info === "left" ? "left-0" : "right-0"
                } w-[70%] h-[100%] z-50 flex items-center justify-center flex-col gap-2 
${props.info === "left" ? '' : 'p-10'}`}
            >
                <h2 className="text-[20px] font-[600]">Samsung Gear VR Camera</h2>
                <span className="text-[20px] text-[#ff5252] font-[600] w-full">
$129.00 
</span>
                <div className="w-full">
                    <Link to="/" className="text-[16px] font-[600] link">SHOP NOW</Link>
                </div>
            </div>
        </div>

        </>

    )
}
export default BannerBoxV2;