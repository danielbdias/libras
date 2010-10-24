function huMoments = calculate_hu_moments()

%auxiliary variables
eta02 = normalized_central_moment(0, 2);
eta03 = normalized_central_moment(0, 3);
eta11 = normalized_central_moment(1, 1);
eta12 = normalized_central_moment(1, 2);
eta20 = normalized_central_moment(2, 0);
eta21 = normalized_central_moment(2, 1);
eta30 = normalized_central_moment(3, 0);

firstHuMoment = eta20 + eta02;

secondHuMoment = (eta20 - eta02)^2 + 4*eta11^2;

thirdHuMoment = (eta30 - 3 * eta12)^2 + (3 * eta21 - eta03)^2;

fourthHuMoment = (eta30 + eta12)^2 + (eta21 + eta03)^2;

fifthHuMoment = (eta30 - 3 * eta12) * (eta30 + eta12) * ((eta30 + eta12)^2 - 3 * (eta21 + eta03)^2) + (3 * eta21 - eta03) * (eta21 + eta03) * (3 * (eta30 + eta12) ^ 2 - (eta21 + eta03)^2);

sixthHuMoment = (eta20 - eta02)*((eta30 + eta12)^2 - (eta21 + eta03)^2) + 4 * eta11 * (eta30 + eta12) * (eta21 + eta03);

seventhHuMoment = (3*eta21 - eta03) * (eta30 + eta12) * ((eta30 + eta12)^2 - 3*(eta21 + eta03)^2);

huMoments(1) = firstHuMoment;
huMoments(2) = secondHuMoment;
huMoments(3) = thirdHuMoment;
huMoments(4) = fourthHuMoment;
huMoments(5) = fifthHuMoment;
huMoments(6) = sixthHuMoment;
huMoments(7) = seventhHuMoment;

end