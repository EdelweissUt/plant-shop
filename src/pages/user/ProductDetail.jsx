import React from 'react';
import {Breadcrumbs} from "@mui/material";
import {Link} from "react-router-dom";
import ProductZoom from "../../components/ProductZoom.jsx";

function ProductDetail(props) {
    return (
        <>
            <section className="py-5 pb-8">
                <div className="container">
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link
                            underline="hover"
                            color="inherit"
                        to="/"
                        className="link transition text-[14px]">
                        Home
                    </Link>\    <Link
                        underline="hover"
                        color="inherit"
                        to="/"
                        className="link transition text-[14px]">
                        Home
                    </Link>
                    </Breadcrumbs>
                </div>
                <div className="container flex gap-4">
                    <div className="productZoomContainer">
                        <ProductZoom/>
                    </div>
                </div>
            </section>

        </>
    );
}
export default ProductDetail;