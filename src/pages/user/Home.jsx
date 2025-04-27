import HomeSlider from "../../components/HomeSlider.jsx";
import CatSlider from "../../components/CatSlider.jsx";
import AdsBannerSlider from "../../components/AdsBannerSlider.jsx";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import {useState} from "react";
import ProductsSlider from "../../components/ProductsSlider.jsx";
import BlogItem from "../../components/BlogItem.jsx";
import Footer from "../../components/Footer.jsx";
import BlogSlider from "../../components/BlogSlider.jsx";
import HomeBannerV2 from "../../components/HomeBannerV2.jsx";
import BannerBoxV2 from "../../components/BannerBox2.jsx";

const Home = () => {
    const products =[
        {
            "id": "1",
            "name": "Cây Lưỡi Hổ",
            "description": "Cây cảnh trong nhà thanh lọc không khí",
            "originalPrice": 150000,
            "discountPrice": 120000,
            "stockQuantity": 50,
            "soldQuantity": 10,
            "size": "Trung bình",
            "category": {
                "id": "c1",
                "name": "Cây trong nhà"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png"
            ]
        },
        {
            "id": "2",
            "name": "Cây Kim Ngân",
            "description": "Mang lại may mắn, thường để bàn làm việc",
            "originalPrice": 200000,
            "discountPrice": 180000,
            "stockQuantity": 30,
            "soldQuantity": 5,
            "size": "Nhỏ",
            "category": {
                "id": "c2",
                "name": "Cây phong thủy"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png",
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744996270/snapedit_1744995898446_bzcodz.png"
            ]
        },
        {
            "id": "3",
            "name": "Cây Trầu Bà",
            "description": "Cây dây leo lọc khí, trang trí tường",
            "originalPrice": 100000,
            "discountPrice": 85000,
            "stockQuantity": 100,
            "soldQuantity": 20,
            "size": "Lớn",
            "category": {
                "id": "c1",
                "name": "Cây trong nhà"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png"
            ]
        },
        {
            "id": "3",
            "name": "Cây Trầu Bà",
            "description": "Cây dây leo lọc khí, trang trí tường",
            "originalPrice": 100000,
            "discountPrice": 85000,
            "stockQuantity": 100,
            "soldQuantity": 20,
            "size": "Lớn",
            "category": {
                "id": "c1",
                "name": "Cây trong nhà"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995025/4859ae1bd1c14568359a7a8aa22f6383_wg4v2d.jpg"
            ]
        },
        {
            "id": "3",
            "name": "Cây Trầu Bà",
            "description": "Cây dây leo lọc khí, trang trí tường",
            "originalPrice": 100000,
            "discountPrice": 85000,
            "stockQuantity": 100,
            "soldQuantity": 20,
            "size": "Lớn",
            "category": {
                "id": "c1",
                "name": "Cây trong nhà"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995025/4859ae1bd1c14568359a7a8aa22f6383_wg4v2d.jpg",
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995026/51775b6384931e24550cb74a10831e73_dmbdco.jpg"
            ]
        },
        {
            "id": "3",
            "name": "Cây Trầu Bà",
            "description": "Cây dây leo lọc khí, trang trí tường",
            "originalPrice": 100000,
            "discountPrice": 85000,
            "stockQuantity": 100,
            "soldQuantity": 20,
            "size": "Lớn",
            "category": {
                "id": "c1",
                "name": "Cây trong nhà"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png"
            ]
        },
        {
            "id": "3",
            "name": "Cây Trầu Bà",
            "description": "Cây dây leo lọc khí, trang trí tường",
            "originalPrice": 100000,
            "discountPrice": 85000,
            "stockQuantity": 100,
            "soldQuantity": 20,
            "size": "Lớn",
            "category": {
                "id": "c1",
                "name": "Cây trong nhà"
            },
            "isHidden": false,
            "isDeleted": false,
            "urlMedias": [
                "https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png"
            ]
        }
    ]


    const categories = [
        {
            name: 'Cây để bàn',
            image: 'https://via.placeholder.com/150?text=Cay1',
        },
        {
            name: 'Cây phong thủy',
            image: 'https://via.placeholder.com/150?text=Cay2',
        },
        {
            name: 'Cây trang trí',
            image: 'https://via.placeholder.com/150?text=Cay3',
        },
        {
            name: 'Cây nội thất',
            image: 'https://via.placeholder.com/150?text=Cay4',
        },
        {
            name: 'Sen đá',
            image: 'https://via.placeholder.com/150?text=Sen+da',
        },
        {
            name: 'Xương rồng',
            image: 'https://via.placeholder.com/150?text=Xuong+rong',
        },
        {
            name: 'Cây trang trí',
            image: 'https://via.placeholder.com/150?text=Cay3',
        },
        {
            name: 'Cây nội thất',
            image: 'https://via.placeholder.com/150?text=Cay4',
        },
        {
            name: 'Sen đá',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995152/istockphoto-154889951-1024x1024-removebg-preview_sqtmmc.png',
        },
        {
            name: 'Xương rồng',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },    {
            name: 'Cây trang trí',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
        {
            name: 'Cây nội thất',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
        {
            name: 'Sen đá',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
        {
            name: 'Xương rồng',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
        {
            name: 'Cây trang trí',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
        {
            name: 'Cây nội thất',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744996269/ef4986a0bc7920e6345719eac7c4b7ea_y2rt2h.jpg',
        },
        {
            name: 'Sen đá',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
        {
            name: 'Xương rồng',
            image: 'https://res.cloudinary.com/dc0vfbssc/image/upload/v1744995033/a19975589872eb8e84781ee5a4373e6f_yb9onn.jpg',
        },
    ];

    const [value, setValue] = useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <>
            <HomeSlider/>
            <CatSlider categories={categories}/>
            <section className='py-6'>
                <div className="container flex items-center ">
                    <div className="part1 w-[75%] mr-4">
                        | <HomeBannerV2 />
                    </div>
                    <div className="part2 w-[30%] flex items-center gap-12 justify-between flex-col mt-6">
                        <BannerBoxV2 info="left" image={'https://demos.codezeel.com/prestashop/PRS21/PRS210502/img/cms/sub-banner-1.jpg'}/>
                        <BannerBoxV2 info="right" image={'https://demos.codezeel.com/prestashop/PRS21/PRS210502/img/cms/sub-banner-2.jpg'}/>
                    </div>
                </div>
            </section>
            <section className='bg-white py-5'>
                <div className="container">
                    <div className="flex items-center justify-between">
                        <div className="leftSec ">
                            <h3 className='font-[50px] font-bold'>Phổ biến</h3>
                        </div>
                        <div className="rightSec w-[65%]">

                                <Tabs
                                    value={value}
                                    onChange={handleChange}
                                    variant="scrollable"
                                    scrollButtons
                                    allowScrollButtonsMobile
                                    aria-label="scrollable force tabs example"
                                >
                                    {
                                        categories?.map((item,index)=>(
                                            <Tab label={item.name}/>
                                        ))
                                    }
                                </Tabs>
                        </div>
                    </div>
                    <ProductsSlider products={products}/>
                </div>
            </section>
            <section className="py-5 bg-white ">
                <div className="container ">
                    <div
                        className="freeShipping w-full md:w-[80%] m-auto py-4 p-4  border-2 border-[#ff5252] flex items-center justify-center lg:justify-between flex-col lg:flex-row rounded-md mb-7">
                        <div className="col1 flex items-center gap-4">
                            <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 32 32"
                                 className="text-[30px] lg:text-[50px]" height="1em" width="1em"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M 0 6 L 0 8 L 19 8 L 19 23 L 12.84375 23 C 12.398438 21.28125 10.851563 20 9 20 C 7.148438 20 5.601563 21.28125 5.15625 23 L 4 23 L 4 18 L 2 18 L 2 25 L 5.15625 25 C 5.601563 26.71875 7.148438 28 9 28 C 10.851563 28 12.398438 26.71875 12.84375 25 L 21.15625 25 C 21.601563 26.71875 23.148438 28 25 28 C 26.851563 28 28.398438 26.71875 28.84375 25 L 32 25 L 32 16.84375 L 31.9375 16.6875 L 29.9375 10.6875 L 29.71875 10 L 21 10 L 21 6 Z M 1 10 L 1 12 L 10 12 L 10 10 Z M 21 12 L 28.28125 12 L 30 17.125 L 30 23 L 28.84375 23 C 28.398438 21.28125 26.851563 20 25 20 C 23.148438 20 21.601563 21.28125 21.15625 23 L 21 23 Z M 2 14 L 2 16 L 8 16 L 8 14 Z M 9 22 C 10.117188 22 11 22.882813 11 24 C 11 25.117188 10.117188 26 9 26 C 7.882813 26 7 25.117188 7 24 C 7 22.882813 7.882813 22 9 22 Z M 25 22 C 26.117188 22 27 22.882813 27 24 C 27 25.117188 26.117188 26 25 26 C 23.882813 26 23 25.117188 23 24 C 23 22.882813 23.882813 22 25 22 Z"></path>
                            </svg>
                            <span className="text-[16px] lg:text-[20px] font-[600] uppercase">Free Shipping </span>
                        </div>
                        <div className="col2"><p className="mb-0 mt-0 font-[500] text-center">Free Delivery Now On Your
                            First Order and over $200</p></div>
                        <p className="font-bold text-[20px] lg:text-[25px]">- Only $200*</p></div>
                    <AdsBannerSlider items={4}/>

                </div>
            </section>
            <section className='bg-white py-5'>
                <div className="container">
                    <div className="flex items-center justify-between">
                        <div className="leftSec ">
                            <h3 className='font-[50px] font-bold'>Mới nhất</h3>
                        </div>
                        <div className="rightSec w-[65%]">

                            <Tabs
                                value={value}
                                onChange={handleChange}
                                variant="scrollable"
                                scrollButtons
                                allowScrollButtonsMobile
                                aria-label="scrollable force tabs example"
                            >
                                {
                                    categories?.map((item,index)=>(
                                        <Tab label={item.name}/>
                                    ))
                                }
                            </Tabs>
                        </div>
                    </div>
                    <ProductsSlider products={products}/>
                </div>
            </section>
            <section className="py-5 bg-white ">
                <div className="container ">
                    <div
                        className="freeShipping w-full md:w-[80%] m-auto py-4 p-4  border-2 border-[#ff5252] flex items-center justify-center lg:justify-between flex-col lg:flex-row rounded-md mb-7">
                        <div className="col1 flex items-center gap-4">
                            <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 32 32"
                                 className="text-[30px] lg:text-[50px]" height="1em" width="1em"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M 0 6 L 0 8 L 19 8 L 19 23 L 12.84375 23 C 12.398438 21.28125 10.851563 20 9 20 C 7.148438 20 5.601563 21.28125 5.15625 23 L 4 23 L 4 18 L 2 18 L 2 25 L 5.15625 25 C 5.601563 26.71875 7.148438 28 9 28 C 10.851563 28 12.398438 26.71875 12.84375 25 L 21.15625 25 C 21.601563 26.71875 23.148438 28 25 28 C 26.851563 28 28.398438 26.71875 28.84375 25 L 32 25 L 32 16.84375 L 31.9375 16.6875 L 29.9375 10.6875 L 29.71875 10 L 21 10 L 21 6 Z M 1 10 L 1 12 L 10 12 L 10 10 Z M 21 12 L 28.28125 12 L 30 17.125 L 30 23 L 28.84375 23 C 28.398438 21.28125 26.851563 20 25 20 C 23.148438 20 21.601563 21.28125 21.15625 23 L 21 23 Z M 2 14 L 2 16 L 8 16 L 8 14 Z M 9 22 C 10.117188 22 11 22.882813 11 24 C 11 25.117188 10.117188 26 9 26 C 7.882813 26 7 25.117188 7 24 C 7 22.882813 7.882813 22 9 22 Z M 25 22 C 26.117188 22 27 22.882813 27 24 C 27 25.117188 26.117188 26 25 26 C 23.882813 26 23 25.117188 23 24 C 23 22.882813 23.882813 22 25 22 Z"></path>
                            </svg>
                            <span className="text-[16px] lg:text-[20px] font-[600] uppercase">Free Shipping </span>
                        </div>
                        <div className="col2"><p className="mb-0 mt-0 font-[500] text-center">Free Delivery Now On Your
                            First Order and over $200</p></div>
                        <p className="font-bold text-[20px] lg:text-[25px]">- Only $200*</p></div>
                    <div className='h-[250px]'>
                        <AdsBannerSlider items={2}/>

                    </div>
                </div>
            </section>
            <section className='bg-white py-5'>
                <div className="container">
                    <div className="flex items-center justify-between">
                        <div className="leftSec ">
                            <h3 className='font-[50px] font-bold'>Nổi bật</h3>
                        </div>
                        <div className="rightSec w-[65%]">

                            <Tabs
                                value={value}
                                onChange={handleChange}
                                variant="scrollable"
                                scrollButtons
                                allowScrollButtonsMobile
                                aria-label="scrollable force tabs example"
                            >
                                {
                                    categories?.map((item,index)=>(
                                        <Tab label={item.name}/>
                                    ))
                                }
                            </Tabs>
                        </div>
                    </div>
                    <ProductsSlider products={products}/>
                </div>
            </section>
            <section className="py-5 bg-white ">
                <div className="container ">
                    <div
                        className="freeShipping w-full md:w-[80%] m-auto py-4 p-4  border-2 border-[#ff5252] flex items-center justify-center lg:justify-between flex-col lg:flex-row rounded-md mb-7">
                        <div className="col1 flex items-center gap-4">
                            <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 32 32"
                                 className="text-[30px] lg:text-[50px]" height="1em" width="1em"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M 0 6 L 0 8 L 19 8 L 19 23 L 12.84375 23 C 12.398438 21.28125 10.851563 20 9 20 C 7.148438 20 5.601563 21.28125 5.15625 23 L 4 23 L 4 18 L 2 18 L 2 25 L 5.15625 25 C 5.601563 26.71875 7.148438 28 9 28 C 10.851563 28 12.398438 26.71875 12.84375 25 L 21.15625 25 C 21.601563 26.71875 23.148438 28 25 28 C 26.851563 28 28.398438 26.71875 28.84375 25 L 32 25 L 32 16.84375 L 31.9375 16.6875 L 29.9375 10.6875 L 29.71875 10 L 21 10 L 21 6 Z M 1 10 L 1 12 L 10 12 L 10 10 Z M 21 12 L 28.28125 12 L 30 17.125 L 30 23 L 28.84375 23 C 28.398438 21.28125 26.851563 20 25 20 C 23.148438 20 21.601563 21.28125 21.15625 23 L 21 23 Z M 2 14 L 2 16 L 8 16 L 8 14 Z M 9 22 C 10.117188 22 11 22.882813 11 24 C 11 25.117188 10.117188 26 9 26 C 7.882813 26 7 25.117188 7 24 C 7 22.882813 7.882813 22 9 22 Z M 25 22 C 26.117188 22 27 22.882813 27 24 C 27 25.117188 26.117188 26 25 26 C 23.882813 26 23 25.117188 23 24 C 23 22.882813 23.882813 22 25 22 Z"></path>
                            </svg>
                            <span className="text-[16px] lg:text-[20px] font-[600] uppercase">Free Shipping </span>
                        </div>
                        <div className="col2"><p className="mb-0 mt-0 font-[500] text-center">Free Delivery Now On Your
                            First Order and over $200</p></div>
                        <p className="font-bold text-[20px] lg:text-[25px]">- Only $200*</p></div>
                    <div className='h-[250px]'>
                        <AdsBannerSlider items={3}/>

                    </div>
                </div>
            </section>
           {/*blog*/}
            <section className='bg-white py-5'>
                <div className="container">
                    <div className="flex items-center justify-between">
                        <div className="leftSec ">
                            <h3 className='font-[50px] font-bold'>BLog</h3>
                        </div>

                    </div>
                    <BlogSlider />
                </div>
            </section>
        </>
    )
}
export default Home;