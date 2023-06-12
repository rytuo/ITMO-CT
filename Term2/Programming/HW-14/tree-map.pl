% map_build(T, RestT, CurTree, CurD, Tree, Hb) :-x
map_build(T, RestT, CurTree, CurD, Tree, Hb) :-
	(T = [] ; CurD = Hb),
	Tree = CurTree,
	RestT = T,
	!.

map_build([(K, V) | T], RestT, CurTree, CurD, Tree, Hb) :-
	map_build(T, RT, 0, 0, RTree, CurD),
	D1 is CurD + 1,
	map_build(RT, RestT, node(K, V, CurTree, RTree), D1, Tree, Hb).

% map_build(ListMap, TreeMap)
map_build(ListMap, TreeMap) :-
	count(ListMap, N),
	height(0, 0, N, H),
	map_build(ListMap, [], 0, 0, TreeMap, H),
	!.

height(K, Cur, N, H) :-
	N =< Cur,
	H = K, !.
height(K, Cur, N, H) :-
	K1 is K + 1,
	C1 is 2 * Cur + 1,
	height(K1, C1, N, H).

count([], S) :- S is 0, !.
count([H | T], S) :- count(T, S1), S is S1 + 1.

map_get(node(Key, V, L, R), Key, Value) :- V = Value, !.
map_get(node(K, V, L, R), Key, Value) :-
	(K < Key ->
		map_get(R, Key, Value);
		map_get(L, Key, Value)).

map_replace(0, _, _, Res) :- Res = 0, !.
map_replace(node(Key, V, L, R), Key, Value, Res) :-
	Res = node(Key, Value, L, R), !.
map_replace(node(K, V, L, R), Key, Value, Res) :-
	(K < Key ->
			map_replace(R, Key, Value, R1),
				Res = node(K, V, L, R1);
			map_replace(L, Key, Value, L1),
				Res = node(K, V, L1, R)).