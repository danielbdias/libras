function m = order_moment(p, q)

syms x y;
m = int(int(x^p*y^q*probability_density_function(x,y)));

end