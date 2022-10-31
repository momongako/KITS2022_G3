import './App.css';

import './Css/style.css';
import './Css/owl-carousel-min.css';

import './Css/profile.css'
import Home from './Pages/Home';
import ShopMainPage from "./Pages/ShopMainPage";
import { BrowserRouter, Routes, Route, useParams, useLocation, Navigate } from "react-router-dom";
import Layout from './Components/Layout';
import Cart from "./Pages/Cart";
import Contact from "./Pages/Contact";
import Checkout from "./Pages/Checkout";

import AdminLayout from "./Components/AdminPageComponents/AdminLayout";
import MainContent from "./Components/AdminPageComponents/MainContent";
import React, { useEffect, useState } from "react";
import DetailProduct from './Pages/DetailProduct';
import SignIn from "./Pages/SignIn";
import Dashboard from "./Components/AdminPageComponents/DashBoard";
import ProfileCustomer from './Pages/ProfileCustomer';
import DetailOrder from './Pages/ProfileComponent/DetailOrder';
import DeleteConfirmModal from "./Components/AdminPageComponents/DeleteConfirmModal";
import Error from "./Pages/ErrorPage";
import AuthService from "./services/AuthService";
import OrderOff from "./testOrderOff/OrderOff";
import AdminNewOrder from "./Components/AdminPageComponents/AdminNewOrder";
import ShopMarkets from './Pages/ShopMarkets';
import {useCart} from "react-use-cart";

function App() {
    const { emptyCart,clearCartMetadata } = useCart();
    const [isAdmin, setAdminRole] = useState(localStorage.getItem('user')?.includes('ADMIN'));
    const [isUser, setUserRole] = useState(localStorage.getItem('user')?.includes('USER'))
    const Login = () => {
        setAdminRole(localStorage.getItem('user')?.includes('ADMIN'));
        setUserRole(localStorage.getItem('user')?.includes('USER'));
    }

    const Logout = () => {
        setAdminRole(false);
        setUserRole(false);
        localStorage.clear()
        window.localStorage.clear()
        localStorage.removeItem('react-use-cart')
        AuthService.logout().then(() => {
            console.log('You have been logged out !!');
        });

    }
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/admin/" element={!isAdmin ? <Navigate to={'/login'} state={"/admin"} replace={true} /> : <AdminLayout onSignOut={Logout} />}>
                    <Route path="/admin/" element={<Dashboard />} />
                    <Route path="/admin/dashboard" element={<Dashboard />} />
                    <Route path="/admin/orders/new"
                        element={<AdminNewOrder />} />
                    <Route path="/admin/orders"
                        element={<MainContent table={"order"} />} />
                    <Route path="/admin/products"
                        element={<MainContent table={"product"} />} />
                    <Route path="/admin/supplier"
                        element={<MainContent table={"supplier"} />} />
                    <Route path="/admin/ranking"
                        element={<MainContent table={"ranking"} />} />
                    <Route path="/admin/rating"
                        element={<MainContent table={"rating"} />} />
                    <Route path="/admin/user"
                        element={<MainContent table={"user"} />} />
                </Route>
                <Route path="/404" element={<Error errorCode={404} message={"WE CAN'T FIND THAT PAGE!"} />} />
                <Route path="/500" element={<Error errorCode={500} message={"SOMETHING WENT WRONG !"} />} />

                <Route path="/" element={<Layout isUser={isUser} onLogout={Logout}/>}>
                    <Route index element={<Home />} />
                    <Route path="/shop/:name" element={<ShopMainPage />} />
                    <Route path="/shop" element={<ShopMainPage />} />
                    <Route path="/cart" element={!isUser ? <Navigate to={'/login'} state={"/cart"} replace={true} /> : <Cart />} />
                    <Route path="/contact" element={<Contact />} />
                    <Route path="/checkout" element={<Checkout />} />
                    <Route path="/login" element={isUser ? <Navigate to={'/'} replace={true} /> : <SignIn onLogin={Login} />} />
                    <Route path="/detail/:name" element={<DetailProduct />} />
                    <Route path="/profile" element={!isUser ? <Navigate to={'/login'} state={"/profile"} replace={true} /> : <ProfileCustomer />} />
                    <Route path="/testdetail" element={<DetailOrder />} />
                    <Route path="/test" element={<DeleteConfirmModal />} />
                    <Route path="/test-order-off" element={<OrderOff />} />
                    <Route path="/shops" element={<ShopMarkets />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
