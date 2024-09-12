import React, { useState, useEffect } from 'react';
import EditingOrderItems from './EditingOrderItems'; // 경로를 적절히 수정해주세요
import './OrderLookup.css';

export function OrderLookup() {
    const [email, setEmail] = useState('');
    const [orders, setOrders] = useState([]);
    const [productMap, setProductMap] = useState(new Map());
    const [error, setError] = useState(null);
    const [editingOrder, setEditingOrder] = useState(null);
    const [showNoItemsMessage, setShowNoItemsMessage] = useState(false); // 새로운 상태 추가

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchOrders = async (email) => {
        try {
            const response = await fetch(`/api/v1/orders/${email}`);
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || '주문 정보를 가져오는 데 실패했습니다.');
            }
            const data = await response.json();
            setOrders(data || []);
            setError(null);
            setShowNoItemsMessage(data.length === 0); // 주문이 없으면 메시지 표시
        } catch (err) {
            console.error('Caught Error:', err.message);
            setOrders([]);
            setError(err.message);
            setShowNoItemsMessage(true); // 오류 발생 시 메시지 표시
        }
    };

    const fetchProducts = async () => {
        try {
            const response = await fetch('/api/v1/products/lists');
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || '제품 정보를 가져오는 데 실패했습니다.');
            }
            const data = await response.json();
            const productMap = new Map();
            data.forEach(product => {
                productMap.set(product.productId, product.productName);
            });
            setProductMap(productMap);
            setError(null);
        } catch (err) {
            console.error('Caught Error:', err.message);
            setError(err.message);
        }
    };

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handleSearch = () => {
        if (email.trim() === '') { // 입력 값이 비어 있는지 확인
            setShowNoItemsMessage(true); // 입력 값이 없으면 메시지 표시
        } else if (productMap.size > 0) {
            fetchOrders(email);
        } else {
            setError('제품 정보를 로드한 후에 주문 정보를 조회할 수 있습니다.');
        }
    };

    const handleEdit = (order) => {
        setEditingOrder(order);
    };

    const handleDelete = async (orderId) => {
        if (window.confirm('정말로 이 주문을 삭제하시겠습니까?')) {
            try {
                const response = await fetch(`/api/v1/orders/${orderId}`, {
                    method: 'DELETE',
                });
                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.error || '주문 삭제에 실패했습니다.');
                }
                setOrders(orders.filter(order => order.orderId !== orderId));
                setError(null);
            } catch (err) {
                console.error('Caught Error:', err.message);
                setError(err.message);
            }
        }
    };

    const handleSave = async (updatedItems) => {
        if (editingOrder) {
            handleEdit();
            try {
                const response = await fetch(`/api/v1/orders/${editingOrder.orderId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        ...editingOrder,
                        orderItem: updatedItems
                    }),
                });
                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.error || '주문 아이템 수정에 실패했습니다.');
                }
                const updatedOrder = await response.json();
                setOrders(orders.map(order => order.orderId === updatedOrder.orderId ? updatedOrder : order));
                setEditingOrder(null);
            } catch (err) {
                console.error('Caught Error:', err.message);
            }
        }
    };

    const handleQuantityChange = (itemId, newQuantity) => {
        setEditingOrder(prevOrder => ({
            ...prevOrder,
            orderItem: prevOrder.orderItem.map(item =>
                item.orderItemId === itemId
                    ? { ...item, newQuantity: newQuantity }
                    : item
            )
        }));
    };

    return (
        <div className="order-lookup-container">
            <input
                type="email"
                value={email}
                onChange={handleEmailChange}
                placeholder="이메일 입력"
            />
            <button onClick={handleSearch}>조회</button>
            {showNoItemsMessage && email.trim() === '' && <p>주문 아이템이 없습니다.</p>} {/* 입력 값이 비어 있을 때 메시지 표시 */}
            {orders.length > 0 && !showNoItemsMessage ? (
                <ul>
                    {orders.map(order => (
                        <li key={order.orderId}>
                            <div>
                                <h3>주문 ID: {order.orderId}</h3>
                                <ul>
                                    {order.orderItem.map(item => (
                                        <li key={item.orderItemId}>
                                            상품 이름: {productMap.get(item.productId) || '알 수 없음'},
                                            카테고리: {item.category},
                                            수량: {item.quantity},
                                            가격: {item.quantity * item.price}원
                                        </li>
                                    ))}
                                </ul>
                                <p>주문 상태: {order.orderStatus === 'NOT_DELIVERY' ? '배송 준비 중' : '배송 중'}</p>
                                {/* 수정과 삭제 버튼 */}
                                <div className="button-container">
                                    {order.orderStatus === 'NOT_DELIVERY' && (
                                        <div>
                                            <button onClick={() => handleEdit(order)}>수정</button>
                                            <button onClick={() => handleDelete(order.orderId)}>삭제</button>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                showNoItemsMessage && email.trim() !== '' && error && <p className="error">{error}</p>  /* 주문이 없을 때 메시지 표시 */
            )}
            {/* 수정 폼 */}
            {editingOrder && (
                <div className="order-edit-form">
                    <h4>주문 수정</h4>
                    <label>
                        이메일:
                        <input
                            type="email"
                            value={editingOrder.email}
                            onChange={(e) => setEditingOrder({ ...editingOrder, email: e.target.value })}
                        />
                    </label>
                    <label>
                        주소:
                        <input
                            type="text"
                            value={editingOrder.address}
                            onChange={(e) => setEditingOrder({ ...editingOrder, address: e.target.value })}
                        />
                    </label>
                    <label>
                        우편번호:
                        <input
                            type="text"
                            value={editingOrder.postCode}
                            onChange={(e) => setEditingOrder({ ...editingOrder, postCode: e.target.value })}
                        />
                    </label>
                </div>
            )}
            {/* 주문 아이템 수정 폼 */}
            {editingOrder && (
                <EditingOrderItems
                    items={editingOrder.orderItem}
                    onQuantityChange={handleQuantityChange}
                    onSave={handleSave}
                    onCancel={() => setEditingOrder(null)}
                />
            )}
        </div>
    );
}
