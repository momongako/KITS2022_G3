import React from "react";

export default function TableMetadata({table, setProps}) {
    switch (table) {
        case "product":
            setProps({
                entity: 'product',
                linkField: 'http://localhost:8080/api/v1/metadata/Product/variables',
                linkAPI: 'http://localhost:8080/api/v1/products?name=&origin=&category=&start=&end=',
                searchLink: 'hhttp://localhost:8080/api/v1/products?origin=&category=&start=&end=&name=',
                havingAddNew: 1,
                havingDelete: 1,
                havingEdit: 1,
            })
            break;

        case "order" :
            setProps({
                entity: 'order',
                linkField: 'http://localhost:8080/api/v1/metadata/Orders/variables',
                linkAPI: 'http://localhost:8080/api/v1/orders/',
                searchLink: 'http://localhost:8080/api/v1/orders',
                havingAddNew: 0,
                havingDelete: 1,
                havingEdit: 1,
            })
            break;
        case "user":
            setProps({
                entity: 'User',
                linkField: 'http://localhost:8080/api/v1/metadata/User/variables',
                linkAPI: 'http://localhost:8080/api/admin/user/',
                searchLink: 'http://localhost:8080/api/admin/user',
                havingAddNew: 1,
                havingDelete: 0,
                havingEdit: 1,
            })
            break;

        case "category" :
            setProps({
                entity: 'category',
                linkField: 'http://localhost:8080/api/v1/metadata/Category/variables',
                linkAPI: 'http://localhost:8080/api/v1/category/',
                searchLink: 'http://localhost:8080/api/v1/category',
                havingAddNew: 1,
                havingDelete: 1,
                havingEdit: 1,
            })
            break;
        case "ranking":
            setProps({
                entity: 'ranking',
                linkField: 'http://localhost:8080/api/v1/metadata/Ranking/variables',
                linkAPI: 'http://localhost:8080/api/v1/rank/',
                searchLink: 'http://localhost:8080/api/v1/rank',
                havingAddNew: 1,
                havingDelete: 1,
                havingEdit: 1,
            })
            break;
        case "rating" :
            setProps({
                entity: 'rating',
                linkField: 'http://localhost:8080/api/v1/metadata/Rating/variables',
                linkAPI: 'http://localhost:8080/api/v1/rating/',
                searchLink: 'http://localhost:8080/api/v1/rating',
                havingAddNew: 0,
                havingDelete: 1,
                havingEdit: 1,
            })
            break;
        case "supplier":
            setProps({
                entity: 'supplier',
                linkField: 'http://localhost:8080/api/v1/metadata/Supplier/variables',
                linkAPI: 'http://localhost:8080/api/v1/supplier/',
                searchLink: 'http://localhost:8080/api/v1/supplier',
                havingAddNew: 1,
                havingDelete: 1,
                havingEdit: 1,
            })
            break;
        default:
            break;
    }
}