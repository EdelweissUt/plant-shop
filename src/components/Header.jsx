import {Link} from "react-router-dom";
import logo from "../assets/images/logo.png"
import Search from "./Search.jsx";
import Navigation from "./Navigation.jsx"
import Badge from '@mui/material/Badge';
import { styled } from '@mui/material/styles';
import IconButton from '@mui/material/IconButton';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {FaRegBell, FaRegHeart, FaShoppingCart} from "react-icons/fa";



function notificationsLabel(count) {
    if (count === 0) {
        return 'no notifications';
    }
    if (count > 99) {
        return 'more than 99 notifications';
    }
    return `${count} notifications`;
}



const Header = () => {
    return (
        <header className="bg-white">
            <div className='top-strip py-2 border-t-[1px] border-gray-250 border-b-[1px]'>
                <div className='container'>
                    <div className="flex items-center justify-center">
                        <div className="col1 w-[50%]">
                            <p className="text-[14px] font-[500]">
                                Get up to 50% off new season styles
                            </p>
                        </div>
                        <div className="col2 flex items-center justify-end">
                            <ul className='flex items-center gap-3'>
                                <li className="list-none">
                                    <Link to="/help-center" className='text-[13px] link font[500] transition'>Help
                                        Center</Link>
                                </li>
                                <li className="list-none">
                                    <Link to="/order-tracking" className='text-[13px] link font[500] transition'>Order
                                        tracking</Link>
                                </li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
            <div className="header py-4 border-b-[1px] border-gray-250 bg-[#F5F5DC] ">
                <div className="container flex items-center justify-between ">
                    <div className="col1 w-[25%] justify-center">
                        <Link to={'/'}><img src={logo} /></Link>
                    </div>
                    <div className="col2 w-[45%]">
                        <Search/>
                    </div>
                    <div className="col3 w-[30%] flex items-center pl-7">
                        <ul className="flex items-center gap-3">
                            <li className="list-none">
                                <Link to={'/login'} className='link transition text-[15px] font-[500]'>Login</Link>&nbsp; | &nbsp;
                                <Link to={'/register'} className='link transition text-[15px] font-[500]'>Register</Link>
                            </li>
                            <li>
                                <IconButton aria-label={notificationsLabel(100)}>
                                    <Badge badgeContent={100} color="secondary">
                                        <FaShoppingCart />
                                    </Badge>
                                </IconButton>

                            </li>
                            <li>

                                <IconButton>
                                    <Badge badgeContent={100} color="secondary">
                                        <FaRegBell />
                                    </Badge>
                                </IconButton>

                            </li>
                            <li>

                                <IconButton>
                                    <Badge badgeContent={100} color="secondary">
                                        <FaRegHeart />
                                    </Badge>
                                </IconButton>

                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <Navigation/>
       </header>
    );
}
export default Header;