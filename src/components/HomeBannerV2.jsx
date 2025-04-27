import React from 'react';
import {Swiper, SwiperSlide} from 'swiper/react';
// Import Swiper styles
import 'swiper/css';
import 'swiper/css/effect-fade';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import {Autoplay, EffectFade, Navigation, Pagination} from 'swiper/modules';
import Button from "@mui/material/Button";
import "./style.css"

function HomeBannerV2(props) {
    return (
        <>
            <Swiper
                spaceBetween={30}
                effect={'fade'}
                navigation={true}
                pagination={{
                    clickable: true,
                }}
                autoplay={true}
                modules={[EffectFade, Navigation, Pagination,Autoplay]}
                className="homeSliderV2"
            >
                <SwiperSlide>
                    <div className="swiper-slide relative overflow-hidden">
                        <img
                            src="https://demos.codezeel.com/prestashop/PRS21/PRS210502/modules/cz_imageslider/views/img/sample-1.jpg"
                            className="w-full"
                        />
                        <div className="slide-info absolute top-0 right-0 w-[50%] h-full z-50 p-8 flex flex-col justify-center">
                            <h4 className="slide-text text-[18px] font-medium mb-3">
                                Big Saving Days Sale
                            </h4>
                            <h2 className="slide-text text-[35px] font-bold mb-3">
                                Women Solid Round Green T-Shirt
                            </h2>
                            <h3 className="slide-text text-[18px] font-medium mb-6">
                                Starting At Only <span className="text-[#ff5252] text-[30px] font-bold">$59.00</span>
                            </h3>
                            <button className="slide-text btn-org w-fit  p-4 rounded-full">SHOP NOW</button>
                        </div>
                    </div>


                </SwiperSlide> <SwiperSlide>
                    <div className="swiper-slide relative overflow-hidden">
                        <img
                            src="https://demos.codezeel.com/prestashop/PRS21/PRS210502/modules/
cz_imageslider/views/img/sample-2.jpg"
                            className="w-full"
                        />
                        <div className="slide-info absolute top-0 right-0 w-[50%] h-full z-50 p-8 flex flex-col justify-center">
                            <h4 className="slide-text text-[18px] font-medium mb-3">
                                Big Saving Days Sale
                            </h4>
                            <h2 className="slide-text text-[35px] font-bold mb-3">
                                Women Solid Round Green T-Shirt
                            </h2>
                            <h3 className="slide-text text-[18px] font-medium mb-6">
                                Starting At Only <span className="text-[#ff5252] text-[30px] font-bold">$59.00</span>
                            </h3>
                            <button className="slide-text btn-org w-fit  p-4 rounded-full">SHOP NOW</button>
                        </div>
                    </div>


                </SwiperSlide>

            </Swiper>
        </>
    );

}

export default HomeBannerV2;

















































