function eta = normalized_central_moment(p, q)

yota = ((p + q) / 2) + 1;

eta = central_moment(p, q) / central_moment(0, 0) ^ yota;