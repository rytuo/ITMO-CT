init(MAX_N) :- cycle(2, MAX_N).

% prime(N)
% composite(N)
% prime_divisors_table(N, R)
cycle(N, N) :- !.
cycle(I, N) :-
    (composite(I) ->
    true;
    assert(prime(I)),
    assert(prime_divisors_table(I, I)),
    Ic is I * I,
    strikeout(Ic, I, N)),
    I1 is I + 1,
    cycle(I1, N).

strikeout(Ic, I, N) :- Ic > N, !.
strikeout(Ic, I, N) :-
    (composite(Ic) ->
        true;
        assert(composite(Ic))),
    assert(prime_divisors_table(Ic, I)),
    I1 is Ic + I,
    strikeout(I1, I, N).

rise(_, []).
rise(N, [H | T]) :- \+ N > H.

% prime_divisors(N, Divisors)
prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :-
    prime_divisors_table(N, H),
    N1 is div(N, H),
    prime_divisors(N1, T),
    rise(H, T).

unique_prime_divisors(N, Unique):-
    prime_divisors(N, Divisors),
    single(1, Divisors, [], Unique).

single(Prev, [], D, U) :-
    U = D, !.
single(Prev, [H | T], D, U) :-
    (H > Prev,
        H1 = [H],
        append(D, H1, D1),
        single(H, T, D1, U)) ;
    (H = Prev,
        single(H, T, D, U)).