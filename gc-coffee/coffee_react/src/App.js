import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, { useEffect, useState } from 'react';
import { ProductList } from "./components/ProductList";
import { Summary } from "./components/Summary";
import { OrderLookup } from "./components/OrderLookup";
import axios from "axios";

function App() {
    const [products, setProducts] = useState([]);
    const [items, setItems] = useState([]);

    const handleAddClicked = productId => {
        const product = products.find(v => v.productId === productId);
        const found = items.find(v => v.productId === productId);
        const updatedItems = found
            ? items.map(v => (v.productId === productId) ? { ...v, count: v.count + 1 } : v)
            : [...items, { ...product, count: 1 }];
        setItems(updatedItems);
    };

    const handleQuantityChange = (updatedItems) => {
        setItems(updatedItems);
    };

    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/products/lists')
            .then(v => setProducts(v.data));
    }, []);

    const handleOrderSubmit = (order) => {
        if (items.length === 0) {
            alert("아이템을 추가해 주세요!");
        } else {
            const orderData = {
                email: order.email,
                address: order.address,
                postCode: order.postcode,
                orderItem: items.map(v => ({
                    productId: v.productId,
                    category: v.category,
                    price: v.price,
                    quantity: v.count
                }))
            };
            console.log("주문 내역 보내기:", orderData);
            axios.post('http://localhost:8080/api/v1/orders', orderData)
                .then(response =>
                        alert(response.data.message || "주문이 정상적으로 접수되었습니다."),
                    error => {
                        alert(error.response?.data?.message || "서버 장애가 발생 했습니다.");
                        console.error(error);
                    })
        }
    };

    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Grids & Circle</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <ProductList products={products} onAddClick={handleAddClicked} />
                    </div>
                    <div className="col-md-4 summary p-4">
                        <Summary items={items} onOrderSubmit={handleOrderSubmit} onQuantityChange={handleQuantityChange} />
                    </div>
                </div>
            </div>
            <div className="mt-4">
                <OrderLookup />  {}
            </div>
        </div>
    );
}

export default App;
