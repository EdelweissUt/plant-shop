import React from 'react';
import {Link} from "react-router-dom";
import Rating from '@mui/material/Rating';
import Button from "@mui/material/Button";
import {MdZoomOutMap} from "react-icons/md";
import {FaRegHeart} from "react-icons/fa";
import './style.css'

function ProductItem(props) {
    return (<>
        <div class="productItem shadow-lg rounded-md overflow-hidden border-1 border-[rgba(0,0,0,0.1)]">
            <div
                className="group imgWrapper w-[100%]  overflow-hidden  rounded-md rounded-bl-none rounded-br-none relative">
                <a href="#" data-discover="true">
                    <div className="img h-[250px] overflow-hidden"><img
                        src={props.item?.urlMedias?.[0]}
                        className="w-full object-fit h-full"/>
                        {
                            props.item?.urlMedias?.[1] &&
                            <img
                                src={props.item?.urlMedias?.[1]}
                                className="w-full transition-all duration-700 absolute top-0 left-0 opacity-0 group-hover:opacity-100 group-hover:scale-105 object-fit h-full"/>
                        }

                    </div>
                </a>
                <span
                    className="discount flex items-center absolute top-[10px] left-[10px] z-50 !bg-primary text-white rounded-lg p-1 text-[12px] font-[500]"
                    style={{background: "red"}}>10%</span>

                <div class="actions absolute top-[-20px] right-[5px] z-50 flex items-center gap-2 flex-col w-[50px] transition-all duration-300 group-hover:top-[15px] opacity-0 group-hover:opacity-100">
                    <Button className="!w-[35px] !h- [35px] !min-w-[35px] !rounded-full !bg-white text-black hover:!bg-[#ff5252] hover:text-white group">
                        <MdZoomOutMap className="text-[18px] !text-black group-hover:text-white hover:!text-white"/>
                    </Button>
                    <Button className="group !bg-white !w-[35px] !h-[35px] !min-w-[35px] !rounded-full text-black hover:!bg-[#ff5252] ">
                        <FaRegHeart className="text-[18px] !text-black  hover:!text-white"/>
                    </Button>
                </div>

                </div>
                <div className="info p-3 shadow-md ">
                    <h6 className='text-[13px] text-gray-700'><Link to={'/'}
                                                                    className='link transition-all'>{props.item.category.name}</Link>
                    </h6>
                    <h3 className='text-[16px] title mt-2 font-[500] font-bold'><Link to={'/'}
                                                                                      className='link transition-all'>{props.item.name}</Link>
                    </h3>
                    <Rating name="size-small" defaultValue={2} size="small" readOnly/>
                    <div className="flex items-center gap-4">
                        <span className='oldPrice line-through'>$58.00</span>
                        <span className='discountPrice text-[#ff5252] font-bold'>$58.00</span>
                    </div>

                </div>
            </div>
        </>
        );
        }

        export default ProductItem;