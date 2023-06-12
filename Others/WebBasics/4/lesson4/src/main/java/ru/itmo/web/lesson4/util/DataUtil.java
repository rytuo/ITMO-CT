package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov"),
            new User(6, "pashka", "Pavel Mavrin"),
            new User(9, "geranazarov555", "Georgiy Nazarov"),
            new User(11, "tourist", "Gennady Korotkevich")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(34, "Bringing a power tool from math into quantum computing", "Though quantum computing remains an enormous technical and intellectual challenge, it has the potential to speed up many programs and algorithms immensely provided that appropriate quantum circuits are designed. In particular, the Fourier transform already has a quantum version called the quantum Fourier transform (QFT), but its applicability is quite limited because its results cannot be used in subsequent quantum arithmetic operations.\n" +
                    "\n" +
                    "To address this issue, in a recent study published in Quantum Information Processing, scientists from Tokyo University of Science developed a new quantum circuit that executes the \"quantum fast Fourier transform (QFFT)\" and fully benefits from the peculiarities of the quantum world. The idea for the study came to Mr. Ryo Asaka, first-year Master's student and one of the scientists on the study, when he first learned about the QFT and its limitations. He thought it would be useful to create a better alternative based on a variant of the standard Fourier transform called the \"fast Fourier transform (FFT),\" an indispensable algorithm in conventional computing that greatly speeds things up if the input data meets some basic conditions.\n" +
                    "\n" +
                    "To design the quantum circuit for the QFFT, the scientists had to first devise quantum arithmetic circuits to perform the basic operations of the FFT, such as addition, subtraction, and digit shifting. A notable advantage of their algorithm is that no \"garbage bits\" are generated; the calculation process does not waste any qubits, the basic unit of quantum information. Considering that increasing the number of qubits of quantum computers has been an uphill battle over the last few years, the fact that this novel quantum circuit for the QFFT can use qubits efficiently is very promising.\n" +
                    "\n" +
                    "Another merit of their quantum circuit over the traditional QFT is that their implementation exploits a unique property of the quantum world to greatly increase computational speed. Associate Professor Kazumitsu Sakai, who led the study, explains: \"In quantum computing, we can process a large amount of information at the same time by taking advantage of a phenomenon known as 'superposition of states.' This allows us to convert a lot of data, such as multiple images and sounds, into the frequency domain in one go.\" Processing speed is regularly cited as the main advantage of quantum computing, and this novel QFFT circuit represents a step in the right direction.\n" +
                    "\n" +
                    "Moreover, the QFFT circuit is much more versatile than the QFT, as Assistant Professor Ryoko Yahagi, who also participated in the study, remarks: \"One of the main advantages of the QFFT is that it is applicable to any problem that can be solved by the conventional FFT, such as the filtering of digital images in the medical field or analyzing sounds for engineering applications.\" With quantum computers (hopefully) right around the corner, the outcomes of this study will make it easier to adopt quantum algorithms to solve the many engineering problems that rely on the FFT.\n" +
                    "\n",6),
            new Post(54, "Assessing state of the art in AI for brain disease treatment", "One tough problem is the diagnosis, surgical treatment, and monitoring of brain diseases. The range of AI technologies available for dealing with brain disease is growing fast, and exciting new methods are being applied to brain problems as computer scientists gain a deeper understanding of the capabilities of advanced algorithms.\n" +
                    "\n" +
                    "In a paper published this week in APL Bioengineering, by AIP Publishing, Italian researchers conducted a systematic literature review to understand the state of the art in the use of AI for brain disease. Their search yielded 2,696 results, and they narrowed their focus to the top 154 most cited papers and took a closer look.\n" +
                    "\n" +
                    "Their qualitative review sheds light on the most interesting corners of AI development. For example, a generative adversarial network was used to synthetically create an aged brain in order to see how disease advances over time.\n" +
                    "\n" +
                    "\"The use of artificial intelligence techniques is gradually bringing efficient theoretical solutions to a large number of real-world clinical problems related to the brain,\" author Alice Segato said. \"Especially in recent years, thanks to the accumulation of relevant data and the development of increasingly effective algorithms, it has been possible to significantly increase the understanding of complex brain mechanisms.\"\n" +
                    "\n" +
                    "The authors' analysis covers eight paradigms of brain care, examining AI methods used to process information about structure and connectivity characteristics of the brain and in assessing surgical candidacy, identifying problem areas, predicting disease trajectory, and for intraoperative assistance. Image data used to study brain disease, including 3D data, such as magnetic resonance imaging, diffusion tensor imaging, positron emission tomography, and computed tomography imaging, can be analyzed using computer vision AI techniques.\n" +
                    "\n" +
                    "But the authors urge caution, noting the importance of \"explainable algorithms\" with paths to solutions that are clearly delineated, not a \"black box\" -- the term for AI that reaches an accurate solution but relies on inner workings that are little understood or invisible.\n" +
                    "\n" +
                    "\"If humans are to accept algorithmic prescriptions or diagnosis, they need to trust them,\" Segato said. \"Researchers' efforts are leading to the creation of increasingly sophisticated and interpretable algorithms, which could favor a more intensive use of 'intelligent' technologies in practical clinical contexts.\"",9),
            new Post (754, "New algorithm could unleash the power of quantum computers", "\"Quantum computers have a limited time to perform calculations before their useful quantum nature, which we call coherence, breaks down,\" said Andrew Sornborger of the Computer, Computational, and Statistical Sciences division at Los Alamos National Laboratory, and senior author on a paper announcing the research. \"With a new algorithm we have developed and tested, we will be able to fast forward quantum simulations to solve problems that were previously out of reach.\"\n" +
                    "\n" +
                    "Computers built of quantum components, known as qubits, can potentially solve extremely difficult problems that exceed the capabilities of even the most powerful modern supercomputers. Applications include faster analysis of large data sets, drug development, and unraveling the mysteries of superconductivity, to name a few of the possibilities that could lead to major technological and scientific breakthroughs in the near future.\n" +
                    "\n" +
                    "Recent experiments have demonstrated the potential for quantum computers to solve problems in seconds that would take the best conventional computer millennia to complete. The challenge remains, however, to ensure a quantum computer can run meaningful simulations before quantum coherence breaks down.\n" +
                    "\n" +
                    "\"We use machine learning to create a quantum circuit that can approximate a large number of quantum simulation operations all at once,\" said Sornborger. \"The result is a quantum simulator that replaces a sequence of calculations with a single, rapid operation that can complete before quantum coherence breaks down.\"\n" +
                    "\n" +
                    "The Variational Fast Forwarding (VFF) algorithm that the Los Alamos researchers developed is a hybrid combining aspects of classical and quantum computing. Although well-established theorems exclude the potential of general fast forwarding with absolute fidelity for arbitrary quantum simulations, the researchers get around the problem by tolerating small calculation errors for intermediate times in order to provide useful, if slightly imperfect, predictions.\n" +
                    "\n" +
                    "In principle, the approach allows scientists to quantum-mechanically simulate a system for as long as they like. Practically speaking, the errors that build up as simulation times increase limits potential calculations. Still, the algorithm allows simulations far beyond the time scales that quantum computers can achieve without the VFF algorithm.\n" +
                    "\n" +
                    "One quirk of the process is that it takes twice as many qubits to fast forward a calculation than would make up the quantum computer being fast forwarded. In the newly published paper, for example, the research group confirmed their approach by implementing a VFF algorithm on a two qubit computer to fast forward the calculations that would be performed in a one qubit quantum simulation.\n" +
                    "\n" +
                    "In future work, the Los Alamos researchers plan to explore the limits of the VFF algorithm by increasing the number of qubits they fast forward, and checking the extent to which they can fast forward systems. The research was published September 18, 2020 in the journal npj Quantum Information.",1),
            new Post(2, "JDK 16: What’s coming in Java 16", "Although not due to arrive until March 2021, Java Development Kit (JDK) 16 has begun to take shape, with proposed features including concurrent thread-stack processing for garbage collection, support for C++ 14 language features, and an “elastic metaspace” capability to more quickly return unused class metadata memory to the OS.\n" +
                    "\n" +
                    "JDK 16 will be the reference implementation of the version of standard Java set to follow JDK 15, which arrived September 15. The six-month release cadence for standard Java would have JDK 16 arriving next March.", 11)
    );

    private static final List<Link> MENU_LINKS = Arrays.asList(
            new Link("Home", "/index"),
            new Link("Help", "/misc/help"),
            new Link("Users", "/users"),
            new Link("Contests", "/contests")
    );

    private static final List<Language> LANGUAGES = Arrays.asList(
            new Language("In English", "/img/gb.png"),
            new Language("In Russian", "/img/ru.png")
    );

    private static final List<AsideText> ASIDE_TEXTS = Arrays.asList(
            new AsideText("Information", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita facilis\n" +
                    "    fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?"),
            new AsideText("Information", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita facilis\n" +
                    "    fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?"),
            new AsideText("Information", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita facilis\n" +
                                  "    fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?")
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);
        data.put("menu_links", MENU_LINKS);
        data.put("languages", LANGUAGES);
        data.put("aside_texts", ASIDE_TEXTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
