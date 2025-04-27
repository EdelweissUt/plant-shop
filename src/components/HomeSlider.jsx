import React, { useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import {Navigation, Pagination, Mousewheel, Keyboard, Autoplay} from 'swiper/modules';
import { motion } from 'framer-motion';

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import './style.css';

const slides = [
    {
        title: 'Cây xanh thanh lọc không khí 🍃',
        description: 'Mang thiên nhiên vào không gian sống của bạn, giúp giảm stress và tăng năng lượng.',
        image: 'https://res.cloudinary.com/dc0vfbssc/video/upload/v1745147635/66823-520427407_small_nvgqpu.mp4',
    },
    {
        title: 'Không gian làm việc xanh mát 💼🌿',
        description: 'Cây để bàn không chỉ đẹp mà còn giúp tập trung và sáng tạo hơn.',
        image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png',
    },
    {
        title: 'Phong thủy – Thu hút tài lộc 💰🌱',
        description: 'Lựa chọn cây hợp mệnh giúp cân bằng năng lượng và thu hút may mắn.',
        image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744996262/6d87e7319d9c887096e2c04353092567-removebg-preview_rg2ywp.png',
    },
];

const isVideo = (url) => {
    return url.match(/\.(mp4|webm|ogg)$/i);
};

function HomeSlider() {
    const [activeIndex, setActiveIndex] = useState(0);

    return (
        <Swiper
            className="mySwiper relative"
            navigation={true}
            pagination={true}
            mousewheel={true}
            keyboard={true}
            autoplay={{ delay: 4000, disableOnInteraction: false }}
            modules={[Navigation, Pagination, Mousewheel, Keyboard,Autoplay]}
            onSlideChange={(swiper) => setActiveIndex(swiper.activeIndex)}
        >
            {slides.map((slide, index) => (
                <SwiperSlide
                    key={index}
                    className="relative bg-[#D9D9D9] min-h-[500px] flex items-center justify-center"
                >
                    {/* Background video hoặc image */}
                    {isVideo(slide.image) ? (
                        <video
                            autoPlay
                            muted
                            loop
                            playsInline
                            className="absolute inset-0 w-full h-full object-cover z-[-1]"
                        >
                            <source src={slide.image} type="video/mp4" />
                            <source src={slide.image.replace('.mp4', '.webm')} type="video/webm" />
                            <source src={slide.image.replace('.mp4', '.ogg')} type="video/ogg" />
                            Your browser does not support the video tag.
                        </video>
                    ) : (
                        <img
                            src={slide.image}
                            alt={slide.title}
                            className="absolute inset-0 w-full h-full object-cover z-[-1] opacity-30"
                        />
                    )}

                    {/* Nội dung slide */}

                    {/* Nội dung slide */}
                    <div className="relative z-10 flex w-full px-6 py-10 flex-col md:flex-row items-center justify-between gap-6">
                        {/* Bên trái: hình ảnh nếu là ảnh */}
                        {!isVideo(slide.image) ? (
                            <motion.div
                                key={`image-${index}-${activeIndex}`}
                                className="w-full md:w-1/2"
                                initial={{ x: -80, opacity: 0 }}
                                animate={{ x: 0, opacity: 1 }}
                                transition={{ duration: 1.5 }}
                            >
                                <img
                                    src={slide.image}
                                    alt={slide.title}
                                    className="rounded-2xl w-full h-[400px] object-contain bg-opacity-30"
                                />
                            </motion.div>
                        ) : (
                            <div className="hidden md:block w-1/2" />
                        )}

                        {/* Bên phải: nội dung chữ */}
                        <motion.div
                            key={`text-${index}-${activeIndex}`}
                            className="w-full md:w-1/2 text-center md:text-left space-y-4"
                            initial={{ x: 80, opacity: 0 }}
                            animate={{ x: 0, opacity: 1 }}
                            transition={{ duration: 1.8, delay: 0.2 }}
                        >
                            <h2 className="text-4xl md:text-5xl font-bold text-[#2E7D32] drop-shadow-md">
                                {slide.title}
                            </h2>
                            <p className="text-white text-lg drop-shadow-md">{slide.description}</p>
                            <button className="px-6 py-2 text-white rounded-full hover:bg-blue-700 transition bg-[#E64A19] font-bold shadow-lg animated-bg">
                                MUA NGAY
                            </button>

                        </motion.div>
                    </div>
                </SwiperSlide>
            ))}
        </Swiper>
    );
}

export default HomeSlider;
