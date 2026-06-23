import { createContext, useContext, useState, ReactNode } from "react";

interface User {
  id: string;
  email: string;
  name: string;
  role: "admin";
}

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const ADMIN_EMAIL = import.meta.env.VITE_ADMIN_EMAIL;
const ADMIN_PASSWORD = import.meta.env.VITE_ADMIN_PASSWORD;

const loadStoredUser = (): User | null => {
  const stored = localStorage.getItem("darsalam_user");
  if (!stored) {
    return null;
  }

  try {
    return JSON.parse(stored) as User;
  } catch {
    localStorage.removeItem("darsalam_user");
    return null;
  }
};

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(loadStoredUser);

  const login = async (email: string, password: string): Promise<boolean> => {
    if (!ADMIN_EMAIL || !ADMIN_PASSWORD) {
      return false;
    }

    await new Promise((resolve) => setTimeout(resolve, 500));

    if (email === ADMIN_EMAIL && password === ADMIN_PASSWORD) {
      const userData: User = {
        id: "1",
        email: ADMIN_EMAIL,
        name: "Administrateur",
        role: "admin",
      };
      setUser(userData);
      localStorage.setItem("darsalam_user", JSON.stringify(userData));
      return true;
    }

    return false;
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("darsalam_user");
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
