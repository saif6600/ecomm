import { useEffect, useState } from 'react';
import api from '../api/client';

export default function OrderHistoryPage() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    api.get('/orders/me').then(({ data }) => setOrders(data)).catch(() => {});
  }, []);

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">My Orders</h2>
      <div className="space-y-3">
        {orders.map((o) => (
          <div key={o.id} className="bg-white border rounded p-4">
            <p className="font-semibold">Order #{o.id} - {o.status}</p>
            <p>Total: ${o.totalAmount}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
