import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, Mousewheel, Keyboard } from 'swiper/modules';
import { motion } from 'framer-motion';
import BannerBox from "./BannerBox.jsx";
function AdsBannerSlider(props) {
    const banners = [
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
        { image: "https://res.cloudinary.com/dc0vfbssc/image/upload/v1745039191/0e681d61-8f2b-415e-b64f-2345adb64729_xnrk1m.png" },
    ];
    return (
        <>

            <div className="adsBannerSlider pt-4 py-8 h-full">
                <div className="container h-full">
                    <Swiper
                     slidesPerView={props.items}
                     spaceBetween={10}
                     navigation={true}
                     modules={[Navigation]}
                     className='mySwiper h-full'
                    >
                        {banners?.map((banner, index) => (
                            <SwiperSlide key={index}>
                                <div className="box bannerBox h-full">
                                    <BannerBox img={banner.image} />
                                </div>
                            </SwiperSlide>
                        ))}

                    </Swiper>



                </div>
            </div>
        </>
    );
}

export default AdsBannerSlider;