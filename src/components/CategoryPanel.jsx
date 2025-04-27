import React, {useState} from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Button from '@mui/material/Button';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import {IoCloseSharp} from "react-icons/io5";
import {Link} from "react-router-dom";
import logo from "../assets/images/logo.png";
import './style.css'



function CategoryPanel(props) {
    const categories = [
        { id: 1, name: "Bonsai" },
        { id: 2, name: "Cây phong thủy" },
        { id: 3, name: "Cây để bàn" },
        { id: 4, name: "Sen đá" },
    ];

    const toggleDrawer = (newOpen) => () => {
        props.setIsOpenCatPanel(newOpen)
    };

    const DrawerList = (
        <Box sx={{ width: 250 }} role="presentation"  className='categoryPanel'>
            <div className="col1 w-[100%] justify-center">
                <Link to={'/'}><img src={logo} /></Link>
            </div>
            <h3 className="p-3 text-[16px] font-[500] flex items-center justify-between">DANH MỤC SẢNH PHẨM <IoCloseSharp onClick={toggleDrawer(false)} className="cursor-pointer text-[20px]"/></h3>

            <div className="scroll">
                <ul className="w-full">
                    {categories.map((cat) => (
                        <li key={cat.id} className='list-none'>
                            <Button
                                className='w-full !text-left !justify-start !px-3 !text-black'
                                onClick={() => {
                                    // bạn có thể xử lý điều hướng tại đây
                                    console.log("Chọn category:", cat.name);
                                }}
                            >
                                {cat.name}
                            </Button>
                        </li>
                    ))}
                </ul>
            </div>


        </Box>
    );

    return (<>
        <div>
            <Drawer open={props.isOpenCatPanel} onClose={toggleDrawer(false)}>
                {DrawerList}
            </Drawer>
        </div>
    </>    );
}

export default CategoryPanel;