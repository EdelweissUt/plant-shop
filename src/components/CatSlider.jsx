import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper/modules';

import 'swiper/css';
import 'swiper/css/pagination';

function CatSlider(props) {
    return (
        <div className="w-[80%] mx-auto mt-8 mb-8" >
            <Swiper
                slidesPerView={8}
                spaceBetween={10}
                centeredSlides={false}
                pagination={{ clickable: true }}
                modules={[Pagination]}
                className="mySwiper h-40"
            >
                {props.categories?.map((cat, index) => (
                    <SwiperSlide key={index} className='bg-white w-18  '>
                        <div className="flex flex-col items-center justify-center w-full bg-[#F5F5DC]">
                            <img
                                src={cat.image}
                                alt={cat.name}
                                className="w-full  h-28 object-contain   hover:scale-90 transition-transform duration-300"
                            />
                            <span className="mt-2 text-sm text-gray-700  text-center font-bold">
            {cat.name}
          </span>
                        </div>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>

    );
}

export default CatSlider;
