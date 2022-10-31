import React from 'react';
import {Outlet, useNavigate} from "react-router-dom";
import AdminSideBar from "./AdminSideBar";



const AdminLayout = ({onSignOut,user}) => {
    return(<>
            <div className="row bg-light bg-gradient">
            <AdminSideBar onSignOut={onSignOut}/>
                    <Outlet />
            </div>
    </>
        )
};

export default AdminLayout;