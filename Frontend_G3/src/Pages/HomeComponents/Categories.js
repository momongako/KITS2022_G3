import React from 'react';
import Slider from "react-slick";
import './Categories.css';
import { useEffect, useState } from 'react';
import CategoriesService from '../../services/CategoriesService';
import ProductService from '../../services/ProductService';
import { Link, useParams } from "react-router-dom";

const Categories = () => {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        CategoriesService.getCategories()
            .then(response => response.data)
            .then((data) => {
                if (data.length > 0) {
                    setCategories(data)
                }

            });

    }, [])
    const settings = {
        dots: true,
        centerPadding: "0px",
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 20000,
        pauseOnHover: true
    };
    return (
        <div className="Categories-top">
            <h4>Các loại sản phẩm</h4>
            <Slider {...settings} >
                {categories != null ?
                    categories.map((item, index) => {
                        return (

                            <div key={index + 1} className="card" style={{ width: '18rem' }}>
                                <Link to={'/shop/' + item.name}>
                                    <img className="" src={item.url} alt="" style={{ width: 250 }} />
                                    <div className="card-body" style={{ height: 80 }}>
                                        <p className="card-text">{item.name}</p>
                                    </div>
                                </Link>
                            </div>


                        )
                    })
                    : "Loading"
                }




            </Slider >


        </div >
    );
};

export default Categories;