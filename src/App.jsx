import {BrowserRouter, Route, Routes} from "react-router-dom";
import UserLayout from "./layouts/UserLayout.jsx";
import AdminLayout from "./layouts/AdminLayout.jsx";
import LoginSignup from "./pages/login/LoginSignup.jsx";
import ProductDetail from "./pages/user/ProductDetail.jsx";


function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path={'/'} exact={true} element={<UserLayout/>}/>
                <Route path="/admin" element={<AdminLayout/>} />
                <Route path="/login" element={<LoginSignup/>} />
                <Route path="/productdetail" element={<ProductDetail/>} />

            </Routes>
        </BrowserRouter>
    )
}

export default App
