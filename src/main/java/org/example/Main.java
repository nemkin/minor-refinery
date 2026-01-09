package org.example;

import tools.refinery.generator.standalone.StandaloneRefinery;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var problem = StandaloneRefinery.getProblemLoader().loadString("""
                import builtin::view.
                import builtin::strategy.
                
                @color(hex="#FFFFFF")
                @hide
                class N{
                    N[] e
                    @decide(false)
                    T t opposite a
                }
                class T {
                    @decide(false)
                    N[1..3] a opposite t
                    T[] i opposite i
                }
                int cost(T t) = count{a(t,_)}.
                int sum() = count{a(_,_)}.
                error pred costly() <-> sum()>10.\s
                
                T(A).T(B).T(C).T(D).T(E).
                atom A,B,C,D,E.
                !exists(T::new).
                default !i(*,*).
                i(A,B).i(B,A).
                i(A,C).i(C,A).
                i(C,B).i(B,C).
                i(A,D).i(D,A).
                i(E,C).i(C,E).
                
                % t(n1,A).
                
                pred u(x,y) <-> e(x,y) ; e(y,x).
                % error pred colorNotUsed(T t) <-> !t(_,t).
                pred typesAreConnected(t1,t2) <->
                    t(n1, t1),
                    t(n2, t2),
                    t1 != t2,
                    u(n1,n2).
                error pred unconnectedInDifferentType(T t1,T t2) <->
                    t1 != t2,
                    i(t1,t2),
                    !typesAreConnected(t1,t2).
                
                
                % error pred unconnectedInSameType(n1, n2) <->
                %     t(n1, A), t(n2, A), n1 != n2, !uA+(n1,n2);
                %     t(n1, B), t(n2, B), n1 != n2, !uB+(n1,n2);
                %     t(n1, C), t(n2, C), n1 != n2, !uC+(n1,n2);
                %     t(n1, D), t(n2, D), n1 != n2, !uD+(n1,n2);
                %     t(n1, E), t(n2, E), n1 != n2, !uE+(n1,n2).
                
                % pred uA(x,y) <-> t(x,A), t(y,A), u(x,y).
                % pred uB(x,y) <-> t(x,B), t(y,B), u(x,y).
                % pred uC(x,y) <-> t(x,C), t(y,C), u(x,y).
                % pred uD(x,y) <-> t(x,D), t(y,D), u(x,y).
                % pred uE(x,y) <-> t(x,E), t(y,E), u(x,y).
                
                decision rule addFirstOfTheColor(N n, T t) <->
                    % must !t(n,_),
                    !must t(_,t)
                ==>
                    t(n,t).
                decision rule addNeighbour(N n, T t) <->
                    u(n,n1),
                    % !must t(n,t),
                    t(n1,t)
                ==>
                    t(n,t).
                
                default !e(*,*).
                !exists(N::new).
                
                % class M.
                e(n0, n4).
                e(n0, n5).
                e(n0, n6).
                e(n0, n7).
                e(n0, n16).
                e(n4, n1).
                e(n4, n2).
                e(n4, n3).
                e(n4, n12).
                e(n5, n1).
                e(n5, n2).
                e(n5, n3).
                e(n5, n13).
                e(n6, n1).
                e(n6, n2).
                e(n6, n3).
                e(n6, n14).
                e(n7, n1).
                e(n7, n2).
                e(n7, n3).
                e(n7, n15).
                e(n1, n17).
                e(n2, n18).
                e(n3, n19).
                e(n16, n20).
                e(n16, n21).
                e(n16, n22).
                e(n16, n23).
                e(n20, n17).
                e(n20, n18).
                e(n20, n19).
                e(n20, n28).
                e(n21, n17).
                e(n21, n18).
                e(n21, n19).
                e(n21, n29).
                e(n22, n17).
                e(n22, n18).
                e(n22, n19).
                e(n22, n30).
                e(n23, n17).
                e(n23, n18).
                e(n23, n19).
                e(n23, n31).
                e(n8, n12).
                e(n8, n13).
                e(n8, n14).
                e(n8, n15).
                e(n8, n24).
                e(n12, n9).
                e(n12, n10).
                e(n12, n11).
                e(n13, n9).
                e(n13, n10).
                e(n13, n11).
                e(n14, n9).
                e(n14, n10).
                e(n14, n11).
                e(n15, n9).
                e(n15, n10).
                e(n15, n11).
                e(n9, n25).
                e(n10, n26).
                e(n11, n27).
                e(n24, n28).
                e(n24, n29).
                e(n24, n30).
                e(n24, n31).
                e(n28, n25).
                e(n28, n26).
                e(n28, n27).
                e(n29, n25).
                e(n29, n26).
                e(n29, n27).
                e(n30, n25).
                e(n30, n26).
                e(n30, n27).
                e(n31, n25).
                e(n31, n26).
                e(n31, n27).
            """);
        try (var generator = StandaloneRefinery.getGeneratorFactory().createGenerator(problem)) {
            generator.generate();
            var trace = generator.getProblemTrace();
            //var childrenRelation = trace.getPartialRelation("Directory::children");
            //var childrenInterpretation = generator.getPartialInterpretation(childrenRelation);
            //var cursor = childrenInterpretation.getAll();
            //while (cursor.move()) {
            //    System.out.printf("%s: %s%n", cursor.getKey(), cursor.getValue());
            //}
        }
    }
}