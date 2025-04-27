import React, {useState} from 'react';
import Sidebar from '../pages/admin/scenes/global/Sidebar.jsx'
import Dashboard from '../pages/admin/scenes/dashboard/index.jsx'
import Topbar from '../pages/admin/scenes/global/Topbar.jsx'
import {useMode} from "../pages/admin/theme.js";
function AdminLayout(props) {
    const [theme, colorMode] = useMode();
    const [isSidebar, setIsSidebar] = useState(true);

    return (

        <>
            <div className="flex relative">
                <Sidebar/>
                <div className="w-full h-full">
                    <Topbar setIsSidebar={setIsSidebar} />
                    <Dashboard/>
                </div>
            </div>
        </>
    );
}

export default AdminLayout;