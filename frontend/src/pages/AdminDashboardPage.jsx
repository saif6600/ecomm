import { useEffect, useState } from 'react';
import api from '../api/client';

const blank = { name: '', description: '', category: '', price: '', stock: '', imageUrl: '' };

export default function AdminDashboardPage() {
  const [users, setUsers] = useState([]);
  const [orders, setOrders] = useState([]);
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState(blank);

  const load = async () => {
    const [usersRes, ordersRes, productsRes] = await Promise.all([
      api.get('/admin/users'), api.get('/admin/orders'), api.get('/products')
    ]);
    setUsers(usersRes.data);
    setOrders(ordersRes.data);
    setProducts(productsRes.data);
  };

  useEffect(() => { load().catch(() => {}); }, []);

  const addProduct = async (e) => {
    e.preventDefault();
    await api.post('/products/admin', { ...form, price: Number(form.price), stock: Number(form.stock) });
    setForm(blank);
    load();
  };

  const deleteProduct = async (id) => {
    await api.delete(`/products/admin/${id}`);
    load();
  };

  return (
    <div className="space-y-8">
      <section className="bg-white border rounded p-4">
        <h2 className="text-xl font-bold mb-3">Add Product</h2>
        <form className="grid md:grid-cols-2 gap-2" onSubmit={addProduct}>
          {Object.keys(form).map((k) => (
            <input key={k} className="border p-2" placeholder={k} value={form[k]} onChange={(e) => setForm({ ...form, [k]: e.target.value })} required={k !== 'imageUrl'} />
          ))}
          <button className="bg-blue-600 text-white py-2 rounded">Save</button>
        </form>
      </section>

      <section>
        <h2 className="text-xl font-bold mb-2">Products</h2>
        {products.map((p) => (
          <div key={p.id} className="bg-white border p-2 rounded mb-2 flex justify-between">
            <span>{p.name} (${p.price})</span>
            <button onClick={() => deleteProduct(p.id)} className="text-red-600">Delete</button>
          </div>
        ))}
      </section>

      <section>
        <h2 className="text-xl font-bold mb-2">Users ({users.length})</h2>
        {users.map((u) => <p key={u.id}>{u.name} - {u.email} - {u.role}</p>)}
      </section>

      <section>
        <h2 className="text-xl font-bold mb-2">Orders ({orders.length})</h2>
        {orders.map((o) => <p key={o.id}>#{o.id} - {o.status} - ${o.totalAmount}</p>)}
      </section>
    </div>
  );
}
