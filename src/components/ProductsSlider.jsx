import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, Mousewheel, Keyboard } from 'swiper/modules';
import ProductItem from "./ProductItem.jsx";
import BannerBox from "./BannerBox.jsx";
function ProductsSlider(props) {
    return (
        <>
            <div className="productsSlider mt-8">

                    <Swiper
                        slidesPerView={5}
                        spaceBetween={10}
                        navigation={true}
                        modules={[Navigation]}
                        className='mySwiper'
                    >
                        {props.products?.map((item, index) => (
                            <SwiperSlide key={index}>
                                    <ProductItem item={item} />
                            </SwiperSlide>
                        ))}

                    </Swiper>



            </div>
        </>
    );
}

export default ProductsSlider;