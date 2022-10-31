import React, { useEffect, useState } from 'react';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';
import { Stepper, Step } from 'react-form-stepper';

import DetailOrder from './ProfileComponent/DetailOrder';
import AuthService from "../services/AuthService";
import AdminService from "../services/AdminService";



const ProfileCustomer = () => {
    const [data,setData] = useState(null)
    const fetchUserInfo = async () => {
        const res = await AdminService.getUserByEmail(eval(localStorage.getItem('email')));
        setData(res.data)
    }

    useEffect(() => {
        fetchUserInfo()
    }, [])

    const [isOpen, setIsOpen] = useState(false);
    function toggleModal() {
        setIsOpen(!isOpen);
    }


    return (
        <div className="ProfileCustomer">
            <main>
                <header>
                    <div className="header-wrap">
                        <div className="profile-pic">
                            <img src={data?.image} alt="profile-logo" />
                        </div>
                        <div className="profile-info">
                            <div className="title row">
                                <h2>{data?.name}</h2>

                            </div>
                            <div className="desktop-only">
                                <div className="details row">
                                    <ul>
                                        <li><button type="button" class="btn btn-light btn-rounded">{data?.ranking?.name}</button></li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                    </div>

                </header>
                <div>
                    <Tabs defaultActiveKey="second" className="col-md-12">
                        {/* tab tài khoản */}
                        <Tab eventKey="first" title="Tài khoản">
                            <section >
                                <div className="container py-5">
                                    <div className="row">
                                        <div className="col-lg-4">
                                            <div className="card mb-4">
                                                <div className="card-body text-center">
                                                    <img src={data?.image} alt="avatar" className="rounded-circle img-fluid" style={{ width: '150px' }} />
                                                    <h5 className="my-3">{data?.name}</h5>
                                                    <div className="d-flex justify-content-center mb-2">
                                                        <button type="button" className="btn btn-primary">Avatar</button>

                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div className="col-lg-8">
                                            <div className="card mb-4">
                                                <div className="card-body">
                                                    <div className="row">
                                                        <div className="col-sm-3">
                                                            <p className="mb-0">Họ tên</p>
                                                        </div>
                                                        <div className="col-sm-9">
                                                            <input className="text-muted mb-0 form-control" type="text" placeholder={data?.name} />
                                                        </div>
                                                    </div>
                                                    <hr />
                                                    <div className="row">
                                                        <div className="col-sm-3">
                                                            <p className="mb-0">Email</p>
                                                        </div>
                                                        <div className="col-sm-9">
                                                            <input className="text-muted mb-0 form-control" type="email" placeholder={data?.email} />
                                                        </div>
                                                    </div>
                                                    <hr />
                                                    <div className="row">
                                                        <div className="col-sm-3">
                                                            <p className="mb-0">Số điện thoại</p>

                                                        </div>
                                                        <div className="col-sm-9">

                                                            <input className="mb-0 form-control" type="email" placeholder={data?.phone} />
                                                        </div>
                                                    </div>
                                                    <hr />


                                                    <div className="row">
                                                        <div className="col-sm-3">
                                                            <p className="mb-0">Địa chỉ</p>
                                                        </div>
                                                        <div className="col-sm-9">
                                                            <input className="text-muted mb-0 form-control" type="email" placeholder={data?.address} />
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div className="text-right" ><button type="button" class="btn btn-primary ">Save</button></div>
                                        </div>
                                    </div>
                                </div>

                            </section>


                        </Tab>
                        {/* end tab tài khoản */}
                        {/* đơn mua */}
                        <Tab eventKey="second" title="Đơn mua">
                            <div><br /></div>
                            <div><h2>Thông tin đơn hàng</h2></div>
                            <br />
                            <div className="card shadow-0 border mb-4">
                                <div className="card-body">
                                    <div className="row d-flex justify-content-between align-items-center">
                                        <div className="col-md-2">
                                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/13.webp" className="img-fluid" alt="Phone" />
                                        </div>
                                        <div className="col-md-3 d-flex flex-column">
                                            <p className="text-muted mb-0">INVOICE#Y34XDHR</p>
                                            <span class="text-muted small">by DHFL on 21 Jan, 2020</span>
                                        </div>
                                        <div className="text-end align-items-center col-md-7    ">
                                            <button onClick={toggleModal} class="btn btn-outline-primary" type="button">Chi tiết đơn hàng</button>
                                        </div>

                                    </div>
                                    <hr className="mb-4" style={{ backgroundColor: '#e0e0e0', opacity: 1 }} />
                                    <div className="row d-flex align-items-center">
                                        <Stepper activeStep={0}>
                                            <Step label="Chờ xác nhận" />
                                            <Step label="Chờ lấy hàng" />
                                            <Step label="Đang giao" />
                                            <Step label="Đã giao" />
                                        </Stepper>
                                    </div>

                                </div>
                            </div>

                            <DetailOrder
                                show={isOpen}
                                onHide={toggleModal}
                            />
                        </Tab>
                        {/* end đơn mua */}
                    </Tabs>
                </div>
            </main >
        </div >
    );
};

export default ProfileCustomer;