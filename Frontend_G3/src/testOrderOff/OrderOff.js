import React from 'react';
import TaskOrder from "./TaskOrder";
import {useRef} from 'react';
import axios from "axios";

const OrderOff = () => {

    const productId = useRef(null);
    const quantity = useRef(null);

    const point = useRef(null);
    const note = useRef(null);
    const addressUser = useRef(null);
    const userName = useRef(null);
    const phoneUser = useRef(null);


    const clickAddOrder = async () => {
        // console.log(productId.current.value);
        // console.log(quantity.current.value);

       await axios.post('http://localhost:8080/api/user/carts',{
            "quantity" :quantity.current.value,
            "productId" : productId.current.value
        })
            .then(function () {
                console.log("them vao order off thanh cong");
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    const clickBuy = async () => {
        // console.log(productId.current.value);
        // console.log(quantity.current.value);

        await axios.post('http://localhost:8080/api/user/carts',{
            "note" : note.current.value,
            "addressUser" : addressUser.current.value,
            "nameUser":userName.current.value,
            "phoneUser":phoneUser.current.value,
            "point" :point.current.value
        })
            .then(function () {
                console.log("them vao order off thanh cong");
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-4">
                            <div className="input-group mb-3">
                                <input ref={productId} type="number" className="form-control" placeholder="Product Id"
                                       aria-label="Product Id"/>
                                    <span className="input-group-text">--</span>
                                    <input ref={quantity} type="number" className="form-control" placeholder="Quantity"
                                           aria-label="Quantity"/>
                            </div>

                                    <button onClick={clickAddOrder} className="site-btn">Add</button>

                </div>
                <div className="col-md-8">
                    <TaskOrder/>
                    <hr/>
                    <div className="input-group mb-3">
                        <span className="input-group-text" id="basic-addon1">Note</span>
                        <input ref={note} type="text" className="form-control" placeholder="Note" aria-label="Note"
                               aria-describedby="basic-addon1"/>
                    </div>

                    <div className="input-group mb-3">
                        <span className="input-group-text" id="basic-addon1">addressUser</span>
                        <input ref={addressUser} type="text" className="form-control" placeholder="addressUser" aria-label="addressUser"
                               aria-describedby="basic-addon1"/>
                    </div>

                    <div className="input-group mb-3">
                        <span className="input-group-text" id="basic-addon1">Username</span>
                        <input ref={userName} type="text" className="form-control" placeholder="Username" aria-label="Username"
                               aria-describedby="basic-addon1"/>
                    </div>

                    <div className="input-group mb-3">
                        <span className="input-group-text" id="basic-addon1">phoneUser</span>
                        <input ref={phoneUser} type="text" className="form-control" placeholder="Username" aria-label="phoneUser"
                               aria-describedby="basic-addon1"/>
                    </div>

                    <div className="input-group mb-3">
                        <span className="input-group-text" id="basic-addon1">point</span>
                        <input ref={point} type="text" className="form-control" placeholder="point" aria-label="point"
                               aria-describedby="basic-addon1"/>
                    </div>

                    <button onClick={clickBuy} className="site-btn">Buy</button>

                </div>

            </div>

        </div>
);
};

export default OrderOff;