import React, {useEffect, useState} from 'react';
import Toast from "react-bootstrap/Toast";
import Image from "react-bootstrap/Image";

const TaskOrder = () => {
    const [data, setData] = useState([]);
    var list = [];

    useEffect(() => {
        let url = 'http://localhost:8080/api/user/carts';
        fetch(url)
            .then((response) => response.json())
            .then((data) => {
                setData(data);
            });
        window.scrollTo({
            top: 15,
            behavior: 'smooth',
        });
    }, [data]);

    const deleteTask = (id) => {
        fetch('http://localhost:8080/api/user/detail-delete/' + id, {
            method: 'DELETE',
        }).then(() => {
            console.log('delete successful!!');
            let result = [...data];
            result = result.filter((item) => {
                return item.id !== id;
            });
            setData(result);
        });
    };

    if (data != null) {
            data.map((item, id) => {
                return list.push(
                    <Toast className="mt-3 col-3 me-3">
                        <Toast.Header onClick={() => deleteTask(item.id)}>
                            <Image src={item.image} style={{ height: '40px' }} />
                            <strong className="me-auto ms-3">Name : {item.name}</strong>
                        </Toast.Header>
                        <Toast.Body> Quantity : {item.quantity}</Toast.Body>
                    </Toast>
                )
            })
            }

                return (
        <div>
            {list}
        </div>
    );
};

export default TaskOrder;