import React, {useState} from 'react';
import Button from "@mui/material/Button";
import {RiMenu2Fill} from "react-icons/ri";
import {LiaAngleDownSolid} from "react-icons/lia";
import {Link} from "react-router-dom";
import {GoRocket} from "react-icons/go";
import CategoryPanel from "./CategoryPanel.jsx";


function Navigation(props) {
    const [isOpenCatPanel, setIsOpenCatPanel] = useState(false)
    const openCategoryPanel =()=>{
        setIsOpenCatPanel(true);
    }
    return (
        <>
        <nav className='bg-[#354A27]'>
            <div className="container flex items-center justify-end gap-8 text-white">
                <div className="col_1 w-[20%]">
                    <Button className="!text-white gap-2 w-full" onClick={openCategoryPanel}>
                        <RiMenu2Fill className="text-[18px]"/> DANH MỤC SẢN PHẨM
                        <LiaAngleDownSolid className="text-[13px] ml-auto font-bold"/>
                    </Button>
                </div>
                <div className="col_2 w-[60%] items-center ">
                    <ul className="flex items-center gap-6 nav">
                        <li className="list-none">
                            <Button> <Link to={"/"} className="link transition text-[14px] font-[500] !text-white
                            hover:!text-[#ff5252]">Trang chủ</Link>
                            </Button>
                        </li>
                        <li className="list-none">
                            <Button> <Link to={"/"} className="link transition text-[14px] font-[500] !text-white
                            hover:!text-[#ff5252]">Khuyến mãi</Link></Button>
                        </li>
                        <li className="list-none">
                            <Button> <Link to={"/"} className="link transition text-[14px] font-[500] !text-white
                            hover:!text-[#ff5252]">Blog</Link>
                            </Button>

                        </li>
                        <li className="list-none relative ">
                            <Button> <Link to={"/"} className="link transition text-[14px] font-[500] !text-white
                            hover:!text-[#ff5252]">Phong thủy</Link>
                            </Button>
                            <div className="submenu absolute top-[120%] left-[0%] min-w-[250px] bg-white
                            shadow-md opacity-0 transition-all">
                                <ul>
                                    <li className='list-none w-full'><Link to={'/'} className='w-full'> <Button className='w-full !text-left !justify-start '>🌱 Cây hút tài lộc</Button></Link></li>
                                    <li className='list-none w-full'><Link to={'/'} className='w-full'> <Button className='w-full !text-left !justify-start'>🍀 Cây trừ tà, xua đuổi âm khí</Button></Link></li>
                                    <li className='list-none w-full'><Link to={'/'} className='w-full'> <Button className='w-full !text-left !justify-start'>🌼 Cây cầu danh vọng, sự nghiệp</Button></Link></li>
                                    <li className='list-none w-full'><Link to={'/'} className='w-full'> <Button className='w-full !text-left !justify-start'>🌸 Cây cầu tình duyên</Button></Link></li>
                                    <li className='list-none w-full'><Link to={'/'} className='w-full'> <Button className='w-full !text-left !justify-start'>🌿 Cây chữa bệnh, lọc không khí</Button></Link></li>
                                </ul>
                            </div>

                        </li>
                        <li className="list-none">
                            <Button> <Link to={"/"} className="link transition text-[14px] font-[500] !text-white
                            hover:!text-[#ff5252]">Mẹo chăm sóc cây</Link>
                            </Button>

                        </li>
                        <li className="list-none">
                            <Button> <Link to={"/"} className="link transition text-[14px] font-[500] !text-white
                            hover:!text-[#ff5252]">Liên hệ</Link>
                            </Button>
                        </li>
                    </ul>

                </div>
                <div className="col_3 w-[20%]">
                    <p className='text-[13px] font[500] flex items-center gap-3 mb-0 mt-0'><GoRocket/>Vận chuyển quốc tế
                    </p>
                </div>
            </div>
        </nav>
{/*
             category panel component
*/}
            <CategoryPanel openCategoryPanel={openCategoryPanel} isOpenCatPanel={isOpenCatPanel} setIsOpenCatPanel={setIsOpenCatPanel}/>
        </>
    );
}

export default Navigation;