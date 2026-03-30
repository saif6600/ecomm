import { Navigate, Route, Routes } from 'react-router-dom';
import Layout from '../components/Layout';
import HomePage from '../pages/HomePage';
import ProductListPage from '../pages/ProductListPage';
import ProductDetailPage from '../pages/ProductDetailPage';
import CartPage from '../pages/CartPage';
import CheckoutPage from '../pages/CheckoutPage';
import OrderHistoryPage from '../pages/OrderHistoryPage';
import AuthPage from '../pages/AuthPage';
import AdminDashboardPage from '../pages/AdminDashboardPage';
import { useAuth } from '../context/AuthContext';

function PrivateRoute({ children }) {
  const { user } = useAuth();
  return user ? children : <Navigate to="/auth" replace />;
}

function AdminRoute({ children }) {
  const { user } = useAuth();
  return user?.role === 'ROLE_ADMIN' ? children : <Navigate to="/" replace />;
}

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="products" element={<ProductListPage />} />
        <Route path="products/:id" element={<ProductDetailPage />} />
        <Route path="cart" element={<PrivateRoute><CartPage /></PrivateRoute>} />
        <Route path="checkout" element={<PrivateRoute><CheckoutPage /></PrivateRoute>} />
        <Route path="orders" element={<PrivateRoute><OrderHistoryPage /></PrivateRoute>} />
        <Route path="admin" element={<AdminRoute><AdminDashboardPage /></AdminRoute>} />
        <Route path="auth" element={<AuthPage />} />
      </Route>
    </Routes>
  );
}
