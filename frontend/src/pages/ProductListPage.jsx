import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/client';

export default function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const { data } = await api.get('/products', { params: { search, category } });
      setProducts(data);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchProducts(); }, []);

  return (
    <div>
      <h2 className="text-2xl font-semibold mb-4">Products</h2>
      <div className="flex gap-2 mb-4">
        <input className="border p-2 rounded" placeholder="Search" value={search} onChange={(e) => setSearch(e.target.value)} />
        <input className="border p-2 rounded" placeholder="Category" value={category} onChange={(e) => setCategory(e.target.value)} />
        <button onClick={fetchProducts} className="bg-slate-800 text-white px-4 rounded">Filter</button>
      </div>
      {loading ? <p>Loading...</p> : (
        <div className="grid md:grid-cols-3 gap-4">
          {products.map((p) => (
            <div key={p.id} className="bg-white border rounded p-4">
              <h3 className="font-semibold">{p.name}</h3>
              <p className="text-sm text-gray-600">{p.category}</p>
              <p className="font-bold mt-2">${p.price}</p>
              <Link to={`/products/${p.id}`} className="text-blue-600">View</Link>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
