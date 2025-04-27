import React from 'react';

function BlogItem() {
    return (
        <div className="w-full h-full">
            <div className="bg-white rounded-xl shadow-md hover:shadow-lg transition duration-300 overflow-hidden h-full flex flex-col">
                <div className="relative flex-[2] h-[50%]">
                    <img
                        src="https://wpriverthemes.com/gixus/wp-content/uploads/2021/08/9.jpg"
                        alt="Announcing if attachment resolution sentiments."
                        className="w-full h-full object-fit hover:scale-125"
                    />
                    <ul className="absolute bottom-0 left-0 flex items-center space-x-3 bg-white bg-opacity-90 px-4 py-1 rounded-br-full rounded-tr-full text-xs w-[70%] h-[30%]">
                        <li>
                            <a
                                href="#"
                                className="text-gray-800 font-bold text-[14px]"
                            >
                                Finance
                            </a>
                        </li>
                        <li className="text-gray-500">
                            August 26, 2021
                        </li>
                    </ul>
                </div>

                <div className="px-4 py-3 flex-[1] flex flex-col justify-between  h-[50%]">
                    <h4 className="text-base font-semibold leading-snug text-gray-900 mb-2">
                        <a
                            href="#"
                            className="hover:text-orange-600"
                        >
                            Announcing if attachment resolution sentiments.
                        </a>
                    </h4>
                    <a
                        href="#"
                        className="inline-flex items-center text-sm text-orange-600 font-medium hover:underline"
                    >
                        READ MORE
                        <svg xmlns="http://www.w3.org/2000/svg" className="ml-1 h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M17 8l4 4m0 0l-4 4m4-4H3" />
                        </svg>
                    </a>
                </div>
            </div>
        </div>
    );
}

export default BlogItem;
