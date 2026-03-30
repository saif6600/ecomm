import { Link, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Layout() {
  const { user, logout } = useAuth();
  return (
    <div className="min-h-screen">
      <header className="bg-white border-b">
        <nav className="max-w-6xl mx-auto p-4 flex justify-between">
          <Link to="/" className="text-xl font-bold">ShopSphere</Link>
          <div className="flex gap-4 items-center">
            <Link to="/products">Products</Link>
            <Link to="/cart">Cart</Link>
            <Link to="/orders">Orders</Link>
            {user?.role === 'ROLE_ADMIN' && <Link to="/admin">Admin</Link>}
            {user ? (
              <button onClick={logout} className="px-3 py-1 bg-slate-800 text-white rounded">Logout</button>
            ) : (
              <Link to="/auth">Login</Link>
            )}
          </div>
        </nav>
      </header>
      <main className="max-w-6xl mx-auto p-4"><Outlet /></main>
    </div>
  );
}
