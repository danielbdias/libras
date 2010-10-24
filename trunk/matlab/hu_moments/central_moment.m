function mi = central_moment(p, q)

syms x y;

sub_x = order_moment(1, 0) / order_moment(0, 0);
sub_y = order_moment(0, 1) / order_moment(0, 0);

mi = int(int((x-sub_x)^p*(y-sub_y)^q*mhi(x, y)));